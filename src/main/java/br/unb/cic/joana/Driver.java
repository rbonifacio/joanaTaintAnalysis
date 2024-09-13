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

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Driver
{
    public static void main(String[] args) throws Exception
    {
        //TODO: Make this flexible.
        String path = "/Users/rbonifacio/Documents/workspace-java/JoanaTaintAnalysis/target/test-classes:/Users/rbonifacio/Documents/workspace-java/JoanaTaintAnalysis/lib/servlet-api.jar";
        JavaMethodSignature entryPoint = JavaMethodSignature.mainMethodOfClass("securibench.micro.basic.Basic1");
        SDGConfig config = new SDGConfig(path, entryPoint.toBCString(), Stubs.JRE_17);

        config.setThirdPartyLibsPath("/Users/rbonifacio/Documents/workspace-java/JoanaTaintAnalysis/lib/servlet-api.jar");
        config.setComputeInterferences(true);
        config.setMhpType(MHPType.PRECISE);
        config.setPointsToPrecision(SDGBuilder.PointsToPrecision.INSTANCE_BASED);
        config.setExceptionAnalysis(SDGBuilder.ExceptionAnalysis.INTERPROC);

        SDGProgram program = SDGProgram.createSDGProgram(config, System.out, new NullProgressMonitor());
        SDGSerializer.toPDGFormat(program.getSDG(), new FileOutputStream("yourSDGFile.pdg"));
        IFCAnalysis analysis = new IFCAnalysis(program);

        SDGMethod context = program.getMethod("securibench.micro.basic.Basic1.main([Ljava/lang/String;)V");
        SDGMethod source = program.getMethod("securibench.micro.basic.Basic1.source()Ljava/lang/String;");
        SDGMethod sink = program.getMethod("securibench.micro.basic.Basic1.sink(Ljava/lang/String;)V");

        analysis.addAnnotation(new IFCAnnotation(AnnotationType.SOURCE, "high", context, source, UnknownCause.INSTANCE));
        analysis.addAnnotation(new IFCAnnotation(AnnotationType.SINK, "low", context, sink, UnknownCause.INSTANCE));


        Collection violations = analysis.doIFC();
        TObjectIntMap var7 = analysis.groupByPPPart(violations);

        Iterator var8 = var7.keySet().iterator();

        while(var8.hasNext()) {
            IViolation var9 = (IViolation)var8.next();
            System.out.println(var9);
        }


    }
}
