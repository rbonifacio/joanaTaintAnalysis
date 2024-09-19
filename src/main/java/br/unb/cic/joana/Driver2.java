package br.unb.cic.joana;

import com.ibm.wala.util.NullProgressMonitor;
import edu.kit.joana.api.IFCAnalysis;
import edu.kit.joana.api.annotations.AnnotationType;
import edu.kit.joana.api.annotations.IFCAnnotation;
import edu.kit.joana.api.annotations.cause.AnnotationCause;
import edu.kit.joana.api.annotations.cause.JavaSourceAnnotation;
import edu.kit.joana.api.annotations.cause.UnknownCause;
import edu.kit.joana.api.sdg.*;
import edu.kit.joana.ifc.sdg.core.violations.IViolation;
import edu.kit.joana.ifc.sdg.graph.SDGSerializer;
import edu.kit.joana.ifc.sdg.mhpoptimization.MHPType;
import edu.kit.joana.ifc.sdg.util.JavaMethodSignature;
import edu.kit.joana.ifc.sdg.util.JavaType;
import edu.kit.joana.util.Stubs;
import edu.kit.joana.wala.core.SDGBuilder;
import gnu.trove.map.TObjectIntMap;
import edu.kit.joana.api.lattice.BuiltinLattices;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
@SuppressWarnings("unchecked")
public class Driver2
{
    public static void main(String[] args) throws Exception
    {
        //TODO: Make this flexible.
        String projectPath = "/usr/app/joanaTaintAnalysis/";
        String path = projectPath + "target/test-classes" + ":" + projectPath + "lib/servlet-api.jar";

        JavaMethodSignature entryPoint = JavaMethodSignature.fromString("samples.Sample2" + (".doGet([Ljava/lang/String;)V"));
        SDGConfig config = new SDGConfig(path, entryPoint.toBCString(), Stubs.JRE_17);

        config.setComputeInterferences(true);
        config.setMhpType(MHPType.PRECISE);
        config.setPointsToPrecision(SDGBuilder.PointsToPrecision.INSTANCE_BASED);
        config.setExceptionAnalysis(SDGBuilder.ExceptionAnalysis.INTERPROC);

        SDGProgram program = SDGProgram.createSDGProgram(config, System.out, new NullProgressMonitor());

        // SDGSerializer.toPDGFormat(program.getSDG(), new FileOutputStream("yourSDGFile.pdg"));
        IFCAnalysis analysis = new IFCAnalysis(program);

        analysis.addSourceAnnotation(program.getPart("samples.Sample2.source()Ljava/lang/String;"), BuiltinLattices.STD_SECLEVEL_HIGH);
		analysis.addSinkAnnotation(program.getPart("samples.Sample2.sink(Ljava/lang/String;)V"), BuiltinLattices.STD_SECLEVEL_LOW);
		
        Collection violations = analysis.doIFC();

        TObjectIntMap var7 = analysis.groupByPPPart(violations);

        Iterator var8 = var7.keySet().iterator();

        while(var8.hasNext()) {
            IViolation var9 = (IViolation)var8.next();
            System.out.println("[violation] : " + var9);
        }
    }
}
