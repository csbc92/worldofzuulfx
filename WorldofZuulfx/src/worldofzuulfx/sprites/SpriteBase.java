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
import worldofzuulfx.Room;

/**
 *
 * @author JV
 */
public abstract class SpriteBase {

    private Image image;
    private ImageView imageView;

    private Pane layer;

    private double dx;
    private double dy;
    private double dr;

    private boolean removable = false;
    private boolean canCollide = false;
    private boolean canTeleport = false;

    private Room nextRoom;
    private double nextPosX;
    private double nextPosY;
    
    private double nextTelePosX;
    private double nextTelePosY;
    

    private double w;
    private double h;

    private boolean canMove = true;
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
        bounds = new Rectangle(h, w);
        bounds.setFill(Color.TRANSPARENT);

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

        bounds = new Rectangle(x, y, this.w, this.h);
        bounds.setFill(Color.TRANSPARENT);
        nextPosX = getBounds().getX();
        nextPosY = getBounds().getY();

        addToLayer();
    }

    public void addToLayer() {
        this.layer.getChildren().add(getBounds());
        this.layer.getChildren().add(this.getImageView());

    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.getImageView());
        this.layer.getChildren().remove(this.getBounds());
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public final double getX() {
        return getBounds().getX();
    }

    public final void setX(double x) {
        getBounds().setX(x);
    }

    public final double getY() {
        return getBounds().getY();

    }

    public final void setY(double y) {
        getBounds().setY(y);
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

    public void move(double x, double y) {

        if (!getCanMove()) {
            return;
        }
        // Important to add 1 pixel. Without it the player can not collides with other objects.
        this.getBounds().setX(x);
        this.getBounds().setY(y);
    }

    public void updateUI() {

        double x = getBounds().getX();
        double y = getBounds().getY();

        getBounds().relocate(x, y);
        getImageView().relocate(getBounds().getX(), getBounds().getY());

    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return getBounds().getX() + getBounds().getWidth() / 2;
    }

    public double getCenterY() {
        return getBounds().getY() + getBounds().getHeight() / 2;
    }
    // TODO Slet CollidesWith
    public boolean collidesWith(SpriteBase otherSprite) {
        boolean collides = false;
//        double i;
//        double l;
//        if (this.canCollide && otherSprite.canCollide) {
//            if (getBounds().getBoundsInParent().intersects(otherSprite.getBounds().getBoundsInParent())) {
//                
//                
//                
//                boolean left = (this.getCenterX() > otherSprite.getCenterX() &&
//                                 (this.getCenterY() > otherSprite.getCenterY() || this.getCenterY() < otherSprite.getCenterY()));
//                
//                boolean right = (this.getCenterX() < otherSprite.getCenterX() &&
//                                (this.getCenterY() > otherSprite.getCenterY() || this.getCenterY() < otherSprite.getCenterY()));
//                
//                boolean top = (this.getCenterX() == otherSprite.getCenterX() && this.getCenterY() > otherSprite.getCenterY());
//                
//                boolean bottom = (this.getCenterX() == otherSprite.getCenterX() && this.getCenterY() < otherSprite.getCenterY());
//                
//                if (right) {
//                    System.out.println("RIGHT");
//                } else if (left) {
//                    System.out.println("LEFT");
//                } else if (top) {
//                    System.out.println("TOP");
//                } else if (bottom) {
//                    System.out.println("BOTTOM");
//                }
//                
//                i = getCenterY(getBounds()) - getCenterY(otherSprite.getBounds());
//                l = getCenterY(otherSprite.getBounds()) - getCenterY(getBounds());

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
//**************************************************************
//                if (getBounds().getBoundsInParent().getMinY() < otherSprite.getBounds().getBoundsInParent().getMinY()
//                        && getBounds().getBoundsInParent().getMaxY() > otherSprite.getBounds().getBoundsInParent().getMinY()) {
//                    actions.put(spriteActions.DOWN, false);
//                    System.out.println("Bottom");
//                } else {
//                    actions.put(spriteActions.UP, false);
//                    System.out.println("Top");
//                }
//                // Player either hits a tile with its left side or right side
//                if (getBounds().getBoundsInParent().getMinX() < otherSprite.getBounds().getBoundsInParent().getMaxX()
//                        && getBounds().getBoundsInParent().getMaxX() > otherSprite.getBounds().getBoundsInParent().getMaxX()) {
//                    actions.put(spriteActions.LEFT, false);
//                    System.out.println("LEFT");
//                } else {
//                    actions.put(spriteActions.RIGHT, false);
//                    System.out.println("Right");
//                }
        return collides;
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
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

    public boolean getCanCollide() {
        return this.canCollide;
    }

    /**
     * @return the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @return the canTeleport
     */
    public boolean canTeleport() {
        return canTeleport;
    }

    /**
     * @param canTeleport the canTeleport to set
     */
    public void setCanTeleport(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }

    public void setTeleport(Room room, double x, double y) {
        nextRoom = room;
        nextTelePosX = x;
        nextTelePosY = y;
        canTeleport = true;
    }

    /**
     * @return the nextRoom
     */
    public Room getNextRoom() {
        return nextRoom;
    }

    /**
     * @param nextPosX the nextPosX to set
     */
    public void setNextPosX(double nextPosX) {
        this.nextPosX = nextPosX;
    }

    /**
     * @param nextPosY the nextPosY to set
     */
    public void setNextPosY(double nextPosY) {
        this.nextPosY = nextPosY;
    }

    /**
     * @return the nextPosY
     */
    public double getNextPosY() {
        return nextPosY;
    }

    /**
     * @return the nextPosX
     */
    public double getNextPosX() {
        return nextPosX;
    }

    /**
     * @return the nextTelePosX
     */
    public double getNextTelePosX() {
        return nextTelePosX;
    }

    /**
     * @param nextTelePosX the nextTelePosX to set
     */
    public void setNextTelePosX(double nextTelePosX) {
        this.nextTelePosX = nextTelePosX;
    }

    /**
     * @return the nextTelePosY
     */
    public double getNextTelePosY() {
        return nextTelePosY;
    }

    /**
     * @param nextTelePosY the nextTelePosY to set
     */
    public void setNextTelePosY(double nextTelePosY) {
        this.nextTelePosY = nextTelePosY;
    }

    /**
     * @return the canMove
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * @param canMove the canMove to set
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

}
