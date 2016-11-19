/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.sprites;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author JV
 */
public class SpriteBase {

    private Image image;
    private ImageView imageView;

    private Pane layer;

    private double dx;
    private double dy;
    private double dr;

    private boolean removable = false;
    private boolean canCollide = false;

    private double w;
    private double h;

    private boolean canMove = true;
    private HashMap<spriteActions, Boolean> actions;
    private Rectangle bounds;

    /**
     *
     * @param image
     */
    public SpriteBase(Image image) {
        this.image = image;
        this.imageView = new ImageView(image);

        this.w = image.getWidth();
        this.h = image.getHeight();
        bounds = new Rectangle(33, 16);
        bounds.setFill(Color.RED);

    }

    /**
     *
     * @param layer
     * @param image
     * @param x
     * @param y
     */
    public SpriteBase(Pane layer, Image image, double x, double y) {
        this(image);

        this.layer = layer;
        this.dx = 0;
        this.dy = 0;
        this.dr = 0;
        this.imageView.relocate(x, y);
        this.actions = new HashMap<>();
        bounds = new Rectangle(x, y, 33, 16);
        bounds.setFill(Color.RED);
        resetActions();
        addToLayer();
    }

    public void addToLayer() {
        this.layer.getChildren().add(getBounds());
        this.layer.getChildren().add(this.getImageView());

    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.getImageView());
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move(spriteActions direction) {

        if (!canMove) {
            return;
        }
        switch (direction) {
            case UP:
                if (actions.get(direction)) {
                    getBounds().setY(getBounds().getY() - dy);
                    resetActions();
                }
                break;

            case DOWN:
                if (actions.get(direction)) {
                    getBounds().setY(getBounds().getY() + dy);
                    resetActions();
                }
                break;

            case RIGHT:
                if (actions.get(direction)) {
                    getBounds().setX(getBounds().getX() + dx);
                    resetActions();
                }
                break;

            case LEFT:
                if (actions.get(direction)) {
                    getBounds().setX(getBounds().getX() - dx);
                    resetActions();
                }
                break;
        }
    }

    public ImageView getView() {
        return getImageView();
    }

    public void updateUI() {
        getImageView().relocate(getBounds().getX(), getBounds().getY() - 16);
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX(Rectangle bounds) {
        return bounds.getX() + bounds.getWidth() / 2;
    }

    public double getCenterY(Rectangle bounds) {
        return bounds.getY() + bounds.getHeight() / 2;
    }

    // TODO: per-pixel-collision
    public boolean collidesWith(SpriteBase otherSprite) {
        boolean collides = false;
        double i;
        double l;
        if (this.canCollide && otherSprite.canCollide) {
            if (getBounds().getBoundsInParent().intersects(otherSprite.getImageView().getBoundsInParent())) {
                   i = getCenterY(getBounds()) - getCenterY(otherSprite.getBounds());
                   l = getCenterY(otherSprite.getBounds()) - getCenterY(getBounds());
 
                // Player either hits a tile with its top or bottom
//                if (getCenterY(getBounds()) - getCenterY(otherSprite.getBounds()) > 0
//                        && (getCenterY(getBounds()) - getCenterY(otherSprite.getBounds()))/2 < 35) {
//                    // Top
//                    actions.put(spriteActions.UP, false);
//
//                }
//                if (getCenterY(otherSprite.getBounds()) - getCenterY(getBounds()) < 0
//                        && getCenterY(otherSprite.getBounds()) - getCenterY(getBounds()) < -200) {
//                    // Bottom
//                    actions.put(spriteActions.DOWN, false);
//                }
//
//                if (getCenterX(getBounds()) - getCenterX(otherSprite.getBounds()) > 0
//                        && getCenterX(getBounds()) - getCenterX(otherSprite.getBounds()) < 35) {
//                    // Left
//                    actions.put(spriteActions.LEFT, false);
//                }
//                if (getCenterX(otherSprite.getBounds()) - getCenterX(getBounds()) < 0
//                        && getCenterX(otherSprite.getBounds()) - getCenterX(getBounds()) < -250) {
//                    //Right
//                    actions.put(spriteActions.RIGHT, false);
//                }

                if (getBounds().getBoundsInParent().getMinY() < otherSprite.getBounds().getBoundsInParent().getMinY()
                        && getBounds().getBoundsInParent().getMaxY() > otherSprite.getBounds().getBoundsInParent().getMinY()) {
                    actions.put(spriteActions.DOWN, false);
                    System.out.println("Bottom");
                } else {
                    actions.put(spriteActions.UP, false);
                    System.out.println("Top");
                }
                // Player either hits a tile with its left side or right side
                if (getBounds().getBoundsInParent().getMinX() < otherSprite.getBounds().getBoundsInParent().getMaxX()
                        && getBounds().getBoundsInParent().getMaxX() > otherSprite.getBounds().getBoundsInParent().getMaxX()) {
                    actions.put(spriteActions.LEFT, false);
                    System.out.println("LEFT");
                } else {
                    actions.put(spriteActions.RIGHT, false);
                    System.out.println("Right");
                }
            }

        }
        return collides;
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return the imageView
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * @param canCollide the canCollide to set
     */
    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    public void resetActions() {
        for (spriteActions action : spriteActions.values()) {
            actions.put(action, true);
        }

    }

    /**
     * @return the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    public enum spriteActions {
        UP, DOWN, LEFT, RIGHT
    }
}
