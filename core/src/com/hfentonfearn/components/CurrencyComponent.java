package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CurrencyComponent implements Component, Poolable {

    public int currency;

    private CurrencyComponent() {}

    public CurrencyComponent init (int currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public void reset() {
        currency = 0;
    }
}
