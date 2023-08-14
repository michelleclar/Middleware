package utils.exception.processor;

import com.google.auto.service.AutoService;
import utils.exception.inter.CatchException;
import utils.exception.inter.CatchExceptions;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: Middleware
 * @description: 异常扫描处理器
 * @author: Mr.Carl
 **/
@AutoService(Processor.class)
public class ExceptionHandlerProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        File outputFile = new File("./annotation_processor_log.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(outputFile));
            writer.println("----------------------------------");
            writer.println("start: --------------");
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(CatchException.class);
            for (Element element : elements) {
                String name = element.getSimpleName().toString();
                writer.println(name + " --> ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //该方法返回ture表示该注解已经被处理, 后续不会再有其他处理器处理; 返回false表示仍可被其他处理器处理.
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<>();
        set.add(CatchException.class.getCanonicalName());
        set.add(CatchExceptions.class.getCanonicalName());
        return set;
    }

}