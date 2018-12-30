package ui;

import action.ConfigBulkAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author linlan.zcj@alibaba-inc.com
 * @date 2018/12/30
 */
public class ConfigBulkDialog extends DialogWrapper {

    private JPanel topPanel;
    private JTextField bulk;
    private JTextField cmdPath;
    private JTextField prefixText;

    public ConfigBulkDialog(@Nullable Project project) {
        super(project);
        initTopPanel();
        setTitle("config oss");
        init();
    }

    private void initTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new VerticalLayout());
        // add bulk
        JPanel bulkPanel = new JPanel();
        bulkPanel.setLayout(new HorizontalLayout());
        JLabel userLabel = new JLabel("bulk:");
        userLabel.setPreferredSize(new Dimension(100, 20));
        bulkPanel.add(userLabel);

        bulk = new JTextField(20);
        bulk.setText(ConfigBulkAction.getBulkConfigStored());
        bulkPanel.add(bulk);
        topPanel.add(bulkPanel);

        //add cmd path
        JPanel cmdPathPanel = new JPanel();
        cmdPathPanel.setLayout(new HorizontalLayout());

        JLabel path = new JLabel("osscmd path:");
        path.setPreferredSize(new Dimension(100, 20));
        cmdPathPanel.add(path);

        cmdPath = new JTextField(20);
        cmdPath.setText(ConfigBulkAction.getCmdPathStored());

        cmdPathPanel.add(cmdPath);

        topPanel.add(cmdPathPanel);

        // add prefix path
        JPanel prefixPanel = new JPanel();
        prefixPanel.setLayout(new HorizontalLayout());

        JLabel prefix = new JLabel("file prefix");
        prefix.setPreferredSize(new Dimension(100, 20));
        prefixPanel.add(prefix);

        prefixText = new JTextField(20);
        prefixText.setText(ConfigBulkAction.getFilePrefixStored());

        prefixPanel.add(prefixText);

        topPanel.add(prefixPanel);

    }

    public String getBulkConfig() {
        return bulk.getText();
    }

    public String getCmdPathConfig() {
        return cmdPath.getText();
    }

    public String getPrefixFileConfig() {
        return prefixText.getText();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return topPanel;
    }
}
