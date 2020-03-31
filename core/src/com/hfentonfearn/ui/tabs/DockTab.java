package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.objects.PlayerUpgrades;
import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeDialog;
import com.hfentonfearn.ui.UpgradeItem;
import com.hfentonfearn.utils.AssetLoader;

public abstract class DockTab {

    protected TextButton btnTab;
    protected Table tabContent;
    protected final Skin skin = AssetLoader.skin;
    protected Array<UpgradeItem> items = new Array<>();
    protected DockDialog dockDialog;

    public DockTab(String tabName, DockDialog dockDialog) {
        btnTab = new TextButton(tabName, skin,"tab");
        this.dockDialog = dockDialog;
        btnTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dockDialog.getTabPane().setActor(tabContent);
            }
        });
        tabContent = new Table();
    }

    public void refresh() {
        tabContent = new Table();
        initialize();
        dockDialog.getTabPane().setActor(tabContent);
    }

    protected abstract void addItem(UpgradeItem parent, String name, String desc, int cost, float value);

    protected ImageButton createButton(int index, UpgradeItem item) {
        ImageButton imageButton = new ImageButton(skin);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(imageButton.getStyle());
        switch (item.type) {
            case SPEED:
                style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnSpeed[index]);
                item.purchased = index <= PlayerUpgrades.speed;
                break;
            case STEERING:
                style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnSteering[index]);
                item.purchased = index <= PlayerUpgrades.steering;
                break;
            case FIRERATE:
                style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnFireRate[index]);
                item.purchased = index <= PlayerUpgrades.cannonFire;
                break;
            case FIRERANGE:
                style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnFireRange[index]);
                item.purchased = index <= PlayerUpgrades.cannonRange;
                break;
            case HULL:
                style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnHull[index]);
                item.purchased = index <= PlayerUpgrades.hull;
                break;
            case CARGO:
                style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnInventory[index]);
                item.purchased = index <= PlayerUpgrades.inventory;
                break;
        }
        imageButton.setStyle(style);
        DockTab tab = this;
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new UpgradeDialog(tab, item).show(dockDialog.getStage());
            }
        });
        return imageButton;
    }

    protected Label getTabLabel(UpgradeItem item) {
        Label l = new Label(item.name, skin);
        Label.LabelStyle style = new Label.LabelStyle(l.getStyle());
        if (item.purchased) {
            style.fontColor = Color.BLACK;
        } else if (item.canPurchase()) {
            style.fontColor = Color.GOLD;
        } else {
            style.fontColor = Color.GRAY;
        }
        l.setStyle(style);
        return l;
    }

    protected void initialize() {
        int index = 0;
        for (UpgradeItem upItem : items) {
            tabContent.add(createButton(index, upItem)).size(70);
            tabContent.add(getTabLabel(upItem)).width(200).padLeft(10);
            tabContent.row();
            index++;
        }
    };

    public Table getTabContent() {
        return tabContent;
    }

    public TextButton getBtnTab() {
        return btnTab;
    }
}
