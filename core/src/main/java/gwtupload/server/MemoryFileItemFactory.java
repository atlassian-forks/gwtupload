/*
 * Copyright 2010 Manuel Carrasco Moñino. (manolo at apache/org)
 * http://code.google.com/p/gwtupload
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtupload.server;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemHeaders;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static gwtupload.shared.UConsts.MULTI_SUFFIX;

/**
 * <p>
 * This factory stores the data of uploaded files in memory.
 * </p>
 *
 * It doesn't support large files.
 * Useful for systems where write to file-system is not allowed.
 *
 * @author Manolo Carrasco Moñino
 *
 */
public class MemoryFileItemFactory implements FileItemFactory, Serializable {

  /**
   * A serializable OutputStream stored in memory.
   *
   */
  public class SerializableByteArrayOutputStream extends OutputStream implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] buff = new byte[requestSize];
    private int size = 0;

    public byte[] get() {
      return buff;
    }

    public void reset() {
      size = 0;
    }

    public int size() {
      return size;
    }

    @Override
    public void write(int b) throws IOException {
      buff[size++] = (byte) b;
    }
  }

  private static final int DEFAULT_REQUEST_SIZE = 4096 * 1024;

  private static final long serialVersionUID = 1L;

  int requestSize;

  private HashMap<String, Integer> map = new HashMap<String, Integer>();

  public MemoryFileItemFactory() {
    this(DEFAULT_REQUEST_SIZE);
  }

  public MemoryFileItemFactory(int requestSize) {
    this.requestSize = requestSize;
  }

  public FileItem createItem(String fieldName, final String contentType, final boolean isFormField, final String fileName) {

    Integer cont = map.get(fieldName) != null ? (map.get(fieldName) + 1): 0;
    map.put(fieldName, cont);
    final String fName = fieldName.replace(MULTI_SUFFIX, "") + "-" + cont;

    return new FileItem() {

      private static final long serialVersionUID = 1L;
      String ctype;
      SerializableByteArrayOutputStream data = new SerializableByteArrayOutputStream();
      String fname;

      boolean formfield;

      String name;
      {
        ctype = contentType;
        fname = fName;
        name = fileName;
        formfield = isFormField;
      }

      public void delete() {
        data.reset();
      }

      public byte[] get() {
        return data.get();
      }

      public String getContentType() {
        return ctype;
      }

      public String getFieldName() {
        return fname;
      }

      public InputStream getInputStream() {
        return new ByteArrayInputStream(get());
      }

      public String getName() {
        return name;
      }

      public OutputStream getOutputStream() {
        return data;
      }

      public long getSize() {
        return data.size();
      }

      public String getString() {
        return data.toString();
      }

      public String getString(String arg0) throws UnsupportedEncodingException {
        return new String(get(), arg0);
      }

      public boolean isFormField() {
        return formfield;
      }

      public boolean isInMemory() {
        return true;
      }

      public void setFieldName(String arg0) {
        fname = arg0;
      }

      public void setFormField(boolean arg0) {
        formfield = arg0;
      }

      public void write(File arg0) {
        throw new UnsupportedOperationException("Writing to file is not allowed");
      }

      public FileItemHeaders getHeaders() {
        return null;
      }

      public void setHeaders(FileItemHeaders headers) {
      }

    };
  }
}
