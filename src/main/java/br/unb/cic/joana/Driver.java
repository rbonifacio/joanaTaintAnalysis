package br.unb.cic.joana;

import com.ibm.wala.util.NullProgressMonitor;
import edu.kit.joana.api.IFCAnalysis;
import edu.kit.joana.api.lattice.BuiltinLattices;
import edu.kit.joana.api.sdg.*;
import edu.kit.joana.ifc.sdg.core.violations.IViolation;
import edu.kit.joana.ifc.sdg.graph.SDGSerializer;
import edu.kit.joana.ifc.sdg.mhpoptimization.MHPType;
import edu.kit.joana.ifc.sdg.util.JavaMethodSignature;
import edu.kit.joana.ui.annotations.PruningPolicy;
import edu.kit.joana.util.Stubs;
import edu.kit.joana.wala.core.SDGBuilder;
import gnu.trove.map.TObjectIntMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Driver {
    private String applicationClassPath;

    private String thirdPartyLibsPath;
    private String entryPointMethod;
    private List<String> sourceMethods;
    private List<String> sinkMethods;
    private SDGConfig config = null;

    private String sdgFile = "sdg";

    public Driver() {
        sourceMethods = new ArrayList<>();
        sinkMethods = new ArrayList<>();
    }

    public Driver(Config config) throws Exception {
        this.applicationClassPath = absolutePath(config.getApplicationClassPath());
        this.entryPointMethod = config.getEntryPointMethod();
        this.thirdPartyLibsPath = String.join(":", config.getThirdPartyLibraries().stream().map(p -> absolutePath(p)).collect(Collectors.toList()));
        this.sourceMethods = config.getSourceMethods();
        this.sinkMethods = config.getSinkMethods();
    }

    public void configure() {
        //JavaMethodSignature entryPoint = JavaMethodSignature.mainMethodOfClass("securibench.micro.basic.Basic1");
        JavaMethodSignature entryPoint = JavaMethodSignature.fromString(entryPointMethod);
        config = new SDGConfig(applicationClassPath, entryPoint.toBCString(), Stubs.JRE_17);

        config.setThirdPartyLibsPath(thirdPartyLibsPath);
        config.setComputeInterferences(true);
        config.setMhpType(MHPType.PRECISE);
        config.setPointsToPrecision(SDGBuilder.PointsToPrecision.OBJECT_SENSITIVE);
        config.setExceptionAnalysis(SDGBuilder.ExceptionAnalysis.INTERPROC);
        config.setFieldPropagation(SDGBuilder.FieldPropagation.OBJ_GRAPH);
    }

    public List<String> execute() throws Exception {
        if (config == null) {
            throw new RuntimeException("Invalid configuration. Did you call the method `configure` before executing the analysis?");
        }

        SDGProgram program = SDGProgram.createSDGProgram(config, System.out, new NullProgressMonitor());
        IFCAnalysis analysis = new IFCAnalysis(program);

        Collection<SDGClass> classes = program.getClasses();

        for (String s : sourceMethods) {
            SDGProgramPart p = program.getPart(s);
            if (p != null) {
                analysis.addSourceAnnotation(p, BuiltinLattices.STD_SECLEVEL_HIGH);
            }
        }

        for (String s : sinkMethods) {
            SDGProgramPart p = program.getPart(s);
            if (p != null) {
                analysis.addSinkAnnotation(program.getPart(s), BuiltinLattices.STD_SECLEVEL_LOW);
            }
        }


        Collection violations = analysis.doIFC();

        List<String> results = new ArrayList<>();

        TObjectIntMap map = analysis.groupByPPPart(violations);

        Iterator it = map.keySet().iterator();

        while (it.hasNext()) {
            IViolation v = (IViolation) it.next();
            results.add(v.toString());
        }

        return results;
    }

    public void setApplicationClassPath(String applicationClassPath) {
        this.applicationClassPath = applicationClassPath;
    }

    public void setThirdPartyLibsPath(String thirdPartyLibsPath) {
        this.thirdPartyLibsPath = thirdPartyLibsPath;
    }

    public void setEntryPointMethod(String entryPointMethod) {
        this.entryPointMethod = entryPointMethod;
    }

    public void setSdgFile(String sdgFile) {
        this.sdgFile = sdgFile;
    }

    public void addSourceMethod(String sourceMethod) {
        this.sourceMethods.add(sourceMethod);
    }

    public void addSinkMethod(String sinkMethod) {
        this.sinkMethods.add(sinkMethod);
    }

    private String absolutePath(String path) {
        if (path.startsWith("~/")) {
            path = path.replace("~", System.getProperty("user.home"));
        }

        File f = new File(path);

        if (!f.exists()) {
            throw new RuntimeException("File " + path + " does not exist");
        }
        return f.getAbsolutePath();
    }
}
