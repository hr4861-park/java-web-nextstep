package core.di.factory;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;

public class BeanFactoryTest {
    private Logger log = LoggerFactory.getLogger(BeanFactoryTest.class);

    private BeanFactory beanFactory;

    @Before
    public void setup() {
        BeanScanner scanner = new BeanScanner("core.di.factory.example");
        Set<Class<?>> preInstanticateClazz = scanner.scan();
        beanFactory = new BeanFactory(preInstanticateClazz);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    public void getControllers() throws Exception {
        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        Set<Class<?>> keys = controllers.keySet();
        for (Class<?> clazz : keys) {
            log.debug("Bean : {}", clazz);
        }
    }
}
