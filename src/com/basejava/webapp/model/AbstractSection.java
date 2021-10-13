package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSection<T> implements Serializable {

    public abstract void setContent(T content);

    public abstract List<String> getContents();

    public abstract int size();
}
