package View;
/**
 * Класс реализации программы - отвечает за вызов всех необоходимых классов.
 * @autor Максим Кузнецов
 * @version 1.0
 */
import Controller.CollectionManager;
import Controller.Commander;
import Controller.PriorityQueueCollector;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static String sysEnvStr;
    public static void main(String[] args) throws IOException {
        Map hashMap = new HashMap();
        hashMap.put("Collmanager_Path","file.xml");
        try {
            setEnv((Map<String, String>) hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите имя переменной окружнения:");
            sysEnvStr = System.getenv(scanner.next());
        }catch (Exception e){
            System.err.println("Программа не нашла перем. окружения с именем Collmanager_Path");
            return;
        }
        PriorityQueueCollector priorityQueueCollector = new PriorityQueueCollector(sysEnvStr);
        priorityQueueCollector.addDataToQueue();
        CollectionManager collectionManager = new CollectionManager(priorityQueueCollector.getPersonPriorityQueue());
        Commander commander = new Commander(collectionManager);
        commander.interactiveMode();

    }
    protected static void setEnv(Map<String, String> newenv) throws Exception {
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        } catch (NoSuchFieldException e) {
            Class[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for(Class cl : classes) {
                if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                    Field field = cl.getDeclaredField("m");
                    field.setAccessible(true);
                    Object obj = field.get(env);
                    Map<String, String> map = (Map<String, String>) obj;
                    map.clear();
                    map.putAll(newenv);
                }
            }
        }
    }
}
