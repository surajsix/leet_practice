import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Random;

public class CodeAnalyzer {

    // Method to display file chooser dialog and get selected file
    private static File selectJavaFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    // Method to read the source code file and compile it
    public static void compileAndRun(File file, int[] array) throws Exception {
        // Read the Java source code
        String sourceCode = readFile(file);

        // Compile the source code
        String className = getClassNameFromFile(file);
        compileSourceCode(file);

        // Load the compiled class
        Class<?> cls = Class.forName(className);

        // Create an instance of the class
        Object obj = cls.getDeclaredConstructor().newInstance();

        // Get the method to be analyzed
        Method method = cls.getMethod("exampleFunction", int[].class);

        // Measure the execution time
        long startTime = System.nanoTime();
        method.invoke(obj, (Object) array);
        long endTime = System.nanoTime();
        long duration = endTime - startTime; // Duration in nanoseconds

        System.out.println("Execution Time: " + duration + " ns");
    }

    // Method to read the file content
    private static String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Method to get class name from file
    private static String getClassNameFromFile(File file) {
        return file.getName().replace(".java", "");
    }

    // Method to compile Java source code
    private static void compileSourceCode(File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new RuntimeException("No Java compiler available. Ensure you are running the JDK.");
        }
        int result = compiler.run(null, null, null, file.getPath());
        if (result != 0) {
            throw new RuntimeException("Compilation failed.");
        }
    }

    public static void main(String[] args) {
        File file = selectJavaFile();
        if (file == null) {
            System.out.println("No file selected.");
            return;
        }

        int[] array = new int[100]; // Example input size

        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }

        try {
            compileAndRun(file, array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
