package gwtupload.client.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface UploadCss extends ClientBundle {
    UploadCss INSTANCE = GWT.create(UploadCss.class);

    @Source("Upload.gss")
    CssResource css();

    @Source("cancel-upld.gif")
    ImageResource imgCancelUpload();

    @Source("cancel-upld-hover.gif")
    ImageResource imgCancelUploadHover();
}