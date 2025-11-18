package com.spacewars.control;

import com.spacewars.Main;
import com.spacewars.model.Team;
import com.spacewars.model.structure.Ship;
import com.spacewars.model.structure.defenses.Armor;
import com.spacewars.model.structure.defenses.Shield;
import com.spacewars.model.structure.weapons.DamageType;
import com.spacewars.util.ImageHelper;
import com.spacewars.view.*;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BattleController {
    private Scene scene;
    private Group root;
    private StarsView starsView;
    private Main application;
    MVCLinkMap<String, TeamView> teamViewLinker = new MVCLinkMap<>();
    MVCLinkMap<String,ShipView> shipViewLinker = new MVCLinkMap<>();
    Timeline timeline = new Timeline();
    AnimationTimer timer;
    ConcurrentLinkedQueue<Runnable> animationQueue = new ConcurrentLinkedQueue<>();

    public BattleController(Main application, Scene scene, Group root) {
        this.scene = scene;
        this.application = application;
        this.root = root;

        starsView = new StarsView(root, 793, 793);

        Team alliedTeam = Team.getTeam(0);
        drawTeam(alliedTeam, 100, 100, true);

        Team enemyTeam = Team.getTeam(1);
        drawTeam(enemyTeam, 100, 650, false);

        timeline.play();

        timer = new AnimationTimer() {
            long lastFrame = 0;
            final long sixtyHertz = 16666666L;
            long targetFrameRate = sixtyHertz * 3;
            @Override
            public void handle(long l) {
                if((l - lastFrame) > targetFrameRate) {
                    lastFrame = l;

                    if(!animationQueue.isEmpty()) animationQueue.poll().run();
                }
            }
        };

        timer.start();
    }

    private TeamView drawTeam(Team team, int x, int y, boolean friendly) {
        int currentX = x, currentY = y;
        TeamView teamView = new TeamView();
        teamViewLinker.put("" + team.hashCode(), teamView);
        teamView.setX(x);
        teamView.setY(y);

        for(Ship ship : team.getShips()) {
            teamView.getShipViews().add(drawShip(ship, currentX, currentY, friendly));
            currentX += 55;
        }

        return teamView;
    }

    private ShipView drawShip(Ship ship, int x, int y, boolean friendly) {
        Color shipColor = ShipView.COLOR_ALLY;
        if(!friendly) shipColor = ShipView.COLOR_ENEMY;

        Shield shield = ship.getShield();
        int shieldValue = (shield == null ? 0 : shield.getMaxIntegrity());
        Armor armor = ship.getArmor();
        int armorValue = (armor == null ? 0 : armor.getMaxIntegrity());

        ShipView shipView = new ShipView(root, ImageHelper.getShipImageFilepath(ship.getName()), x, y, shipColor,
                shieldValue, armorValue, ship.getIntegrityMax());

        root.getChildren().add(shipView);

        /*
        so we can look up hash-code of model object to view object and the other way around
        why not use model object? hash-code should stay constant for your objects
        objects may be altered (or destroyed) on the worker thread by the time the animation is displayed
        */
        shipViewLinker.put("" + ship.hashCode(), shipView);

        return shipView;
    }

    public void requestFire(Ship attacker, Ship defender, DamageType damageType,
                            int shieldDamage, int armorDamage, int hullDamage, boolean crit) {
        ShipView attackerView = shipViewLinker.getView("" + attacker.hashCode());

        requestFire(attacker, defender, damageType, shieldDamage, armorDamage, hullDamage,
                attackerView.getWidth()/5, crit);
    }

    public void requestFire(Ship attacker, Ship defender, DamageType damageType,
                     int shieldDamage, int armorDamage, int hullDamage, double variation, boolean crit) {
        animationQueue.add(() -> fire(attacker, defender, damageType,
                shieldDamage, armorDamage, hullDamage, variation, crit));
    }

    private void fire(Ship attacker, Ship defender, DamageType damageType,
                            int shieldDamage, int armorDamage, int hullDamage, boolean crit) {
        ShipView attackerView = shipViewLinker.getView("" + attacker.hashCode());
        fire(attacker, defender, damageType, shieldDamage, armorDamage, hullDamage,
                attackerView.getWidth()/5, crit);
    }

    private void fire(Ship attacker, Ship defender, DamageType damageType,
                            int shieldDamage, int armorDamage, int hullDamage, double variation, boolean crit) {
        ShipView attackerView = shipViewLinker.getView("" + attacker.hashCode());
        ShipView defenderView = shipViewLinker.getView("" + defender.hashCode());

        Random random = new Random();

        double attackerX = attackerView.getLayoutX() + attackerView.getWidth() / 2;
        double attackerY = attackerView.getLayoutY() + attackerView.getWidth() / 2;
        double defenderX = defenderView.getLayoutX() + defenderView.getWidth() / 2 +
                random.nextDouble() * 2 * variation - variation;
        double defenderY = defenderView.getLayoutY() + defenderView.getWidth() / 2 +
                random.nextDouble() * 2 * variation - variation;

        ZapView zapView;
        switch(damageType) {
            case EM:
                zapView = new ZapView(attackerX, attackerY, defenderX, defenderY, Color.BLACK, Color.CORNFLOWERBLUE);
                break;
            case KINETIC:
                zapView = new ZapView(attackerX, attackerY, defenderX, defenderY, Color.BLACK, Color.WHITE);
                break;
            case EXPLOSIVE:
                zapView = new ZapView(attackerX, attackerY, defenderX, defenderY, Color.BLACK, Color.YELLOW);
                break;
            case THERMAL:
                zapView = new ZapView(attackerX, attackerY, defenderX, defenderY, Color.BLACK, Color.RED);
                break;
            default:
                zapView = new ZapView(attackerX, attackerY, defenderX, defenderY, Color.BLACK, Color.GREEN);
                break;
        }

        root.getChildren().add(zapView);

        FadeTransition fade = new FadeTransition();
        fade.setNode(zapView);
        fade.setFromValue(1);
        fade.setToValue(0.0);
        fade.setDuration(Duration.millis(1500));

        fade.setOnFinished( e -> {
            root.getChildren().remove(zapView);
            zapView.destroy();
        });

        fade.play();

        HealthBarView health = defenderView.getHealthBarView();
        health.setValues(health.getShieldValue() - shieldDamage,
                health.getArmorValue() - armorDamage,
                health.getHullValue() - hullDamage);

        if(crit) {
            double x = defenderView.getLayoutX();
            double y = defenderView.getLayoutY();
            ImageView imageView = ImageHelper.createImageAtLocation("Explosion.png", x + 15, y + 15,
                    20, 20);
            imageView.setOpacity(.5);
            root.getChildren().add(imageView);

            ScaleTransition st = new ScaleTransition(Duration.millis(2000), imageView);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(0);
            st.setToY(0);
            st.setOnFinished( e2 -> root.getChildren().remove(imageView));

            st.play();
        }
    }

    public void requestDestroy(Ship ship) {
        animationQueue.add(() -> destroy(ship));
    }

    private void destroy(Ship ship) {
        ShipView shipView = shipViewLinker.getView("" + ship.hashCode());
        double x = shipView.getLayoutX();
        double y = shipView.getLayoutY();
        ImageView imageView = ImageHelper.createImageAtLocation("Explosion.png", x, y,
                50, 50);
        imageView.setOpacity(.5);
        GaussianBlur imageBlur = new GaussianBlur();
        imageBlur.setRadius(5);
        imageView.setEffect(imageBlur);


        FadeTransition fade = new FadeTransition();
        fade.setNode(shipView);
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setDuration(Duration.millis(2000));

        fade.setOnFinished( e -> {
            root.getChildren().add(0, imageView);
            shipView.destroy();
            shipViewLinker.removeView(shipView);

            ScaleTransition st = new ScaleTransition(Duration.millis(4000), imageView);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(0);
            st.setToY(0);
            st.setOnFinished( e2 -> root.getChildren().remove(imageView));

            st.play();
        });

        fade.play();
    }

    public void requestRegenerateShield(Ship ship, int shieldAfterRegen) {
        animationQueue.add(() -> regenerateShield(ship, shieldAfterRegen));
    }

    private void regenerateShield(Ship ship, int shieldAfterRegen) {
        ShipView shipView = shipViewLinker.getView("" + ship.hashCode());
        HealthBarView health = shipView.getHealthBarView();
        health.setValues(shieldAfterRegen, health.getArmorValue(), health.getHullValue());
    }

    public MVCLinkMap<String, ShipView> getShipViewLinker() {
        return shipViewLinker;
    }

    public MVCLinkMap<String, TeamView> getTeamViewLinker() {
        return teamViewLinker;
    }
}