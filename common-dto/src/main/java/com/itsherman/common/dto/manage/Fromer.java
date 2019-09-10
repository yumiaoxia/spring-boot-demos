package com.itsherman.common.dto.manage;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class Fromer {

    private Selector selector;

    private Object srcObject;

    public Fromer(Selector selector) {
        this.selector = selector;
    }

    public TransFormer from(Object srcObj) {
        this.srcObject = srcObj;
        return new TransFormer(this);
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public Object getSrcObject() {
        return srcObject;
    }

    public void setSrcObject(Object srcObject) {
        this.srcObject = srcObject;
    }
}
