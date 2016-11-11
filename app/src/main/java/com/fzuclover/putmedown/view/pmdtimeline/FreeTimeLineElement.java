package com.fzuclover.putmedown.view.pmdtimeline;

import java.io.Serializable;

/**
 * Created by lkl on 2016/11/11.
 */

public class FreeTimeLineElement implements Serializable {

    private String parent;
    private String child;
    private String left;
    private boolean isSuccess;

    public FreeTimeLineElement() {

    }

    public FreeTimeLineElement(String parent, String child, String left) {
        this.parent = parent;
        this.child = child;
        this.left = left;
    }

    public FreeTimeLineElement(String parent, String child, String left, boolean isSuccess){
        this.parent = parent;
        this.child = child;
        this.left = left;
        this.isSuccess = isSuccess;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


}
