package br.com.pattern.commons.impl.validation.entity;

public class AnyEntity {

    private String name;
    private AnyInnerEntity anyInnerEntity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnyInnerEntity getAnyInnerEntity() {
        return anyInnerEntity;
    }

    public void setAnyInnerEntity(AnyInnerEntity anyInnerEntity) {
        this.anyInnerEntity = anyInnerEntity;
    }
}
