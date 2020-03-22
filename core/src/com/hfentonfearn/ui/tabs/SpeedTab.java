package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;

public class SpeedTab extends DockTab {

    /*
    Torn Rags = 20f
    Flax = 30f
    Hemp = 35f
    Cotton = 40f
    Silk = 50f
    Cursed = 60f
     */
    private Array<UpgradeButton> btns = new Array<>();

    public SpeedTab(Container<Table> tabPane) {
        super("Speed", tabPane);
        btns.add(createButton(null,0,20,0, true));
        btns.add(createButton(btns.get(0),1,30, 300,false));
        btns.add(createButton(btns.get(1),2,35, 500,false));
        btns.add(createButton(btns.get(2),3,40, 600,false));
        btns.add(createButton(btns.get(3),4,50, 800,false));
        btns.add(createButton(btns.get(4),5,60, 1000,false));

        for (UpgradeButton btn : btns) {
            tabContent.add(btn);
            tabContent.row();
        }
    }

    private UpgradeButton createButton(UpgradeButton parent, int index, float speed, int cost, boolean purchased) {
        UpgradeButton upgradeButton = new UpgradeButton(parent, purchased, cost);
        ImageButton.ImageButtonStyle style = upgradeButton.getStyle();
        style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnSails[index]);
        upgradeButton.setStyle(style);
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int currency = Components.CURRENCY.get(PlayerComponent.player).currency;
                if (cost <= currency) {
                    Components.STATS.get(PlayerComponent.player).setSpeed(speed);
                    Components.CURRENCY.get(PlayerComponent.player).currency -= cost;
                    upgradeButton.purchased = true;
                    for (UpgradeButton btn : btns)
                        btn.checkParent();
                } else {
                    // Cannot afford the upgrade
                }
            }
        });
        return upgradeButton;
    }

    private class UpgradeButton extends ImageButton {
        private UpgradeButton parent;
        private boolean purchased;
        private int cost;

        public UpgradeButton(UpgradeButton parent, boolean purchased, int cost){
            super(skin);
            this.parent = parent;
            this.purchased = purchased;
            this.cost = cost;
            setDisabled(true);
            checkParent();
        }

        public void checkParent() {
            if (purchased) {
                setDisabled(true);
            } else {
                if (parent.isPurchased()) {
                    setDisabled(false);
                } else {
                    setDisabled(true);
                }
            }
        }

        public boolean isPurchased() {
            return purchased;
        }

        public int getCost() {
            return cost;
        }
    }
}
