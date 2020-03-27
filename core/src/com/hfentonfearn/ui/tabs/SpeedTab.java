package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.objects.PlayerUpgrades;
import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeDialog;
import com.hfentonfearn.utils.AssetLoader;

public class SpeedTab extends DockTab {

    /*
    Torn Rags = 20f
    Flax = 30f
    Hemp = 35f
    Cotton = 40f
    Silk = 50f
    Cursed = 60f
    */

    private Array<UpgradeItem> items = new Array<>();
    private DockDialog dockDialog;

    public SpeedTab(DockDialog dialog) {
        super("Speed", dialog.getTabPane());
        dockDialog = dialog;

        UpgradeItem item = new UpgradeItem(null,"Torn Rags", "These torn rags barely help you get you ship from A to B.", 0, 20);
        items.add(item);
        item = new UpgradeItem(item,"Flax Sails", "These basic sails catch the wind enough to get the ship moving.", 300, 30);
        items.add(item);
        item = new UpgradeItem(item,"Hemp Sails", "Although pretty heavy, these hemp sails are more resilient.", 500, 35);
        items.add(item);
        item = new UpgradeItem(item,"Cotton Sails", "Light weight cotton is stitched to to provide a good speed.", 600, 40);
        items.add(item);
        item = new UpgradeItem(item, "Silk Sails", "Luxury sails fit only for kings increase the speed drastically.", 800, 50);
        items.add(item);
        item = new UpgradeItem(item, "Cursed Sails", "Sails cursed with the souls of dead sailors. Their spirits carry the wind.", 1000, 60);
        items.add(item);

        initialize();
    }

    private ImageButton createButton(int index, UpgradeItem item) {
        item.purchased = index <= PlayerUpgrades.speed;
        ImageButton imageButton = new ImageButton(skin);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(imageButton.getStyle());
        style.imageUp = new TextureRegionDrawable(AssetLoader.ui.docks.upgBtnSails[index]);
        imageButton.setStyle(style);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new UpgradeDialog(item).show(dockDialog.getStage());
                //TODO this doesnt work
                refresh();
            }
        });
        return imageButton;
    }

    @Override
    protected void initialize() {
        int index = 0;
        for (UpgradeItem upItem : items) {
            tabContent.add(createButton(index, upItem)).size(70);
            Label l = new Label(upItem.name, skin);
            Label.LabelStyle style = new Label.LabelStyle(l.getStyle());
            if (upItem.purchased) {
                style.fontColor = Color.DARK_GRAY;
            } else if (upItem.canPurchase()) {
                style.fontColor = Color.GOLD;
            } else {
                style.fontColor = Color.BLACK;
            }
            l.setStyle(style);
            tabContent.add(l).width(200).padLeft(10);
            tabContent.row();
            index++;
        }
    }
}
