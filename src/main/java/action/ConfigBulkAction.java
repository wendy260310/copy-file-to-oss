package action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import ui.ConfigBulkDialog;

/**
 * @author linlan.zcj@alibaba-inc.com
 * @date 2018/12/30
 */
public class ConfigBulkAction extends AnAction {

    public static final String BULK_CONFIG_KEY = "OSS_BULK_KEY";
    public static final String CMD_PATH_KEY = "OSS_CMD_PATH_KEY";
    public static final String FILE_PREFIX_KEY = "OSS_PREFIX_KEY";

    public static String getBulkConfigStored() {
        return PropertiesComponent.getInstance().getValue(BULK_CONFIG_KEY);
    }

    public static String getCmdPathStored() {
        return PropertiesComponent.getInstance().getValue(CMD_PATH_KEY);
    }

    public static String getFilePrefixStored() {
        return PropertiesComponent.getInstance().getValue(FILE_PREFIX_KEY);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        ConfigBulkDialog dialog = new ConfigBulkDialog(project);
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            PropertiesComponent.getInstance().setValue(BULK_CONFIG_KEY, dialog.getBulkConfig());
            PropertiesComponent.getInstance().setValue(CMD_PATH_KEY, dialog.getCmdPathConfig());
            PropertiesComponent.getInstance().setValue(FILE_PREFIX_KEY, dialog.getPrefixFileConfig());
            Messages.showMessageDialog(project, "Config success",
                "Notice", Messages.getInformationIcon());
        }
    }
}
