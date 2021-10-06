package com.basejava.webapp.model;

import java.io.Serializable;

public abstract class AbstractSection<T> implements Serializable {

    public abstract void setContent(T content);
}
