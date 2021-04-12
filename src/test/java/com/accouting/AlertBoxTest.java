import javafx.scene.control.Label;
import org.junit.Test;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class AlertBoxTest extends TestFXBase {

    @Test
    public void errorMessageAppearsIfEnteredNotValidUsernameOrPasswordsWhenLogin() {
        clickOn("#loginBtn");
        verifyThat("#loginPrompt", (Label textArea) -> {
            String text = textArea.getText();
            return text.contains("Username or password is wrong");
        });
    }

    @Test
    public void errorMessageAppearsIfCategoryNotSelected() {
        clickOn("#usernameTextFieldLogin").write("test");
        clickOn("#passwordFieldLogin").write("test123");
        clickOn("#loginBtn");
        sleep(500);
        clickOn("#accessSelectedRootCategoryBtn");
        TestHelper.alert_dialog_has_header_and_content("Category not selected", "Please select a category you would like to access");
    }

    @Test
    public void errorDialogIsShownWhenEmailValueNotEntered() {
        clickOn("#registerBtn");
        verifyThat("OK", NodeMatchers.isVisible());
        TestHelper.alert_dialog_has_header_and_content("Email not valid", "Email is not in valid format, please enter email correctly.");
    }

    @Test
    public void errorDialogIsShownWhenEnteredNotValidEmail() {
        clickOn("#emailTextFieldRegister").write("abc");
        clickOn("#registerBtn");
        verifyThat("OK", NodeMatchers.isVisible());
        TestHelper.alert_dialog_has_header_and_content("Email not valid", "Email is not in valid format, please enter email correctly.");
    }

}
