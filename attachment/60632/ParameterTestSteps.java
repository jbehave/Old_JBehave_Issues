
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Parameters;

import java.util.Map;

public class ParameterTestSteps {

    @Given("parameters: $table")
    public void readParameters(ExamplesTable table) {
        for (Map<String, String> row : table.getRows()) {
            System.out.println(row.get("column1")+" "+row.get("column2"));
        }
    }

}
