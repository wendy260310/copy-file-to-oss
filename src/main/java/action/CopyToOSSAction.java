package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linlan.zcj@alibaba-inc.com
 * @date 2018/12/29
 */
public class CopyToOSSAction extends AnAction {

    private Exception e;

    private static final Logger log = Logger.getInstance(CopyToOSSAction.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        String cmd = ConfigBulkAction.getCmdPathStored();
        String bulk = ConfigBulkAction.getBulkConfigStored();
        if (StringUtils.isEmpty(cmd) || StringUtils.isEmpty(bulk)) {
            Messages.showErrorDialog("please config oss cmd first ", "Notice");
            return;
        }
        VirtualFile file = event.getData(DataKeys.VIRTUAL_FILE);
        if (file.isDirectory()) {
            int ret = Messages.showOkCancelDialog("Are you sure to copy all files in this directory to oss?",
                "Notice", Messages.getInformationIcon());
            if (ret == Messages.CANCEL) {
                return;
            }
        }
        e = null;
        AtomicInteger total = new AtomicInteger(0);
        AtomicInteger error = new AtomicInteger(0);
        uploadToOss(file, cmd, bulk, ConfigBulkAction.getFilePrefixStored(),
            total, error);
        if (e != null) {
            if (e instanceof FileNotFoundException) {
                Messages.showErrorDialog("osscmd not found,make sure you " + cmd + " exists ",
                    "Notice");
            }
        }

        Messages.showInfoMessage(" total :" + total.get() + ", error:" + error.get(),
            "Notice");

    }

    private void uploadToOss(VirtualFile f,
                             String cmd, String bulk, String filePrefix, AtomicInteger total, AtomicInteger error) {
        if (!f.isDirectory()) {
            String finalCmd = cmd + " put " + f.getPath() +
                " oss://" + bulk + "/" + ((StringUtils.isEmpty(filePrefix)) ? "" : filePrefix + "/")
                + f.getName();

            try {
                Process pr = Runtime.getRuntime().exec(finalCmd);
                total.incrementAndGet();
                pr.waitFor();
                if (pr.exitValue() != 0) {
                    error.incrementAndGet();
                    log.error(new String(IOUtils.toByteArray(pr.getInputStream()),
                        "UTF-8"));
                }
            } catch (IOException | InterruptedException e) {
                this.e = e;
                log.error(e);
                e.printStackTrace();
            }
        } else {
            for (VirtualFile tmp : f.getChildren()) {
                uploadToOss(tmp, cmd, bulk, filePrefix, total, error);
            }
        }
    }
}
