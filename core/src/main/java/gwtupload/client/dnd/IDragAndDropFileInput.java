package gwtupload.client.dnd;

import gwtupload.client.FileList;

/**
 * IDragAndDropFileInput.
 *
 * @author Sultan Tezadov
 * @author Manolo Carrasco Moñino
 */
public interface IDragAndDropFileInput {

    boolean hasFiles();

    FileList getFiles();

    String getName();

    void reset();

    void lock();
}
