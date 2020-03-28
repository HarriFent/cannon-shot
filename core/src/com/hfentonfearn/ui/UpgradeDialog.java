package com.hfentonfearn.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.objects.PlayerUpgrades;
import com.hfentonfearn.ui.tabs.DockTab;
import com.hfentonfearn.ui.tabs.SpeedTab;
import com.hfentonfearn.ui.tabs.UpgradeItem;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class UpgradeDialog extends Dialog {

    private Skin skin = AssetLoader.skin;
    private UpgradeItem item;

    public UpgradeDialog(DockTab tab, UpgradeItem item) {
        super(item.name, AssetLoader.skin);
        this.item = item;
        setPosition((Gdx.graphics.getWidth() * 0.5f) - (getWidth() * 0.5f),
                (Gdx.graphics.getHeight() * 0.5f) - (getHeight() * 0.5f));
        setMovable(false);
        setModal(true);

        getContentTable().add(new Label(item.description, skin));
        getContentTable().row();
        getContentTable().add(new Label("Upgrade Cost: " + item.cost, skin)).left();
        getContentTable().row();
        getContentTable().add(new Label("Speed level: " + item.value, skin)).left();
        getContentTable().row();

        getButtonTable().add(getUpgradeButton(tab)).expand().fillX();
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        getButtonTable().add(closeButton).expand().fillX();
    }

    private TextButton getUpgradeButton(DockTab tab) {
        String label = "Purchase";
        boolean disabled = false;
        if (item.purchased) {
            label = "Owned";
            disabled = true;
        }
        if (item.parent != null) {
            if (!item.parent.purchased) {
                label = "Must Own Previous Upgrade";
                disabled = true;
            }
        }
        TextButton upgradeButton = new TextButton(label, skin);
        upgradeButton.setDisabled(disabled);
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int currency = Components.CURRENCY.get(PlayerComponent.player).currency;
                if (item.cost <= currency) {
                    Components.STATS.get(PlayerComponent.player).setSpeed(item.value);
                    Components.CURRENCY.get(PlayerComponent.player).currency -= item.cost;
                    item.purchased = true;
                    PlayerUpgrades.speed++;
                    tab.refresh();
                    hide();
                }
            }
        });
        return upgradeButton;
    }
}