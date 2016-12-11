package worldofzuulfx.sprites;

/**
 * A Spritebase represents all visual object throughout the game. E.g. Items,
 * tiles, player and NPCs. A Spritebase has the ability to move and collide.
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import worldofzuulfx.Room.Room;

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
    private String nextPos;
    private double nextPosX;
    private double nextPosY;

    private double nextTelePosX;
    private double nextTelePosY;

    private double w;
    private double h;

    private boolean canMove = true;
    private Rectangle bounds;

    /**
     * This constructor sets the image, the height and width of the spritebase
     * using a rectangle.
     *
     * @param image The image which represent the spritebase
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
     * This constructor sets the image, layer and the position of the
     * spritebase.
     *
     * @param layer The layer which the spritebase is drawn on.
     * @param image The image that represents the spritebase.
     * @param x The x-coordinate of the spritebase (Relative to the layer)
     * @param y The y-coordinate of the spritebase (Relative to the layer)
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

    /**
     * Add itself to the layer to be shown.
     */
    public void addToLayer() {
        this.layer.getChildren().add(getBounds());
        this.layer.getChildren().add(this.getImageView());

    }

    /**
     * Remove itself from the layer
     */
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.getImageView());
        this.layer.getChildren().remove(this.getBounds());
    }

    /**
     *
     * @return The layer containing the spritebase
     */
    public Pane getLayer() {
        return layer;
    }

    /**
     * Set the layer which the spritebase can be drawn on.
     *
     * @param layer
     */
    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    /**
     *
     * @return The x-coordinate of the spritebase rectangle
     */
    public final double getX() {
        return getBounds().getX();
    }

    /**
     * Set the x-coordinate of the spritebase rectangle
     *
     * @param x
     */
    public final void setX(double x) {
        getBounds().setX(x);
    }

    /**
     *
     * @return The y-coordinate of the spritebase rectangle
     */
    public final double getY() {
        return getBounds().getY();

    }

    /**
     * Set the y-coordinate of the spritebase rectangle
     *
     * @param y
     */
    public final void setY(double y) {
        getBounds().setY(y);
    }

    /**
     *
     * @return The number of pixels which the spritebase uses move along the
     * x-axis
     */
    public double getDx() {
        return dx;
    }

    /**
     * set the number of pixels which the spritebase uses move along the x-axis
     *
     * @param dx
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     *
     * @return The number of pixels which the spritebase uses move along the
     * y-axis
     */
    public double getDy() {
        return dy;
    }

    /**
     * Set the number of pixels which the spritebase uses move along the y-axis
     *
     * @param dy
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Moves the spritebase's rectangle to a given x-,y-position.
     *
     * @param x
     * @param y
     */
    public void move(double x, double y) {

        if (!getCanMove()) {
            return;
        }

        this.getBounds().setX(x);
        this.getBounds().setY(y);
    }

    /**
     * Updates the position of the image relative to the rectangle.
     */
    public void updateUI() {

        double x = getBounds().getX();
        double y = getBounds().getY();

        getImageView().relocate(x, y);

    }

    /**
     *
     * @return The width of the spritebase's rectangle.
     */
    public double getWidth() {
        return w;
    }

    /**
     *
     * @return The height of the spritebase's rectangle.
     */
    public double getHeight() {
        return h;
    }

    /**
     *
     * @return Get the center x-coordinate of the spritebase's rectangle
     */
    public double getCenterX() {
        return getBounds().getX() + getBounds().getWidth() / 2;
    }

    /**
     *
     * @return Get the center y-coordinate of the spritebase's rectangle
     */
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
     * @return the imageView
     */
    public ImageView getImageView() {

        return imageView;
    }

    /**
     * @param canCollide True if the spritebase can collide otherwise false
     */
    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }

    /**
     *
     * @return True if the spritebase can collide
     */
    public boolean getCanCollide() {
        return this.canCollide;
    }

    /**
     * @return The spritebase's rectangle
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @return True if the spritebase can teleport another object - e.g. Player.
     */
    public boolean canTeleport() {
        return canTeleport;
    }

    /**
     * @param canTeleport True if the spritebase can teleport another object -
     * e.g. Player. Otherwise false.
     *
     */
    public void setCanTeleport(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }

    /**
     * Set which room and position of the room, the spritebase can teleport
     * another object to. CanTeleport is set True automatically.
     *
     * @param room
     * @param nextPos
     */
    public void setTeleport(Room room, String nextPos) {
        nextRoom = room;
        this.nextPos = nextPos;

        canTeleport = true;
    }

    /**
     * @return The room which the spritebase can teleport another object to.
     */
    public Room getNextRoom() {
        return nextRoom;
    }

    /**
     * Set the next x-coordinate is used when moving to check if the spritebase
     * collides with another spritebase.
     *
     * @param nextPosX
     */
    public void setNextPosX(double nextPosX) {
        this.nextPosX = nextPosX;
    }

    /**
     * Set the next y-coordinate is used when moving to check if the spritebase
     * collides with another spritebase.
     *
     * @param nextPosY
     */
    public void setNextPosY(double nextPosY) {
        this.nextPosY = nextPosY;
    }

    /**
     * @return The next y-coordinate is used when moving to check if the
     * spritebase collides with another spritebase.
     */
    public double getNextPosY() {
        return nextPosY;
    }

    /**
     * @return The next x-coordinate is used when moving to check if the
     * spritebase collides with another spritebase.
     */
    public double getNextPosX() {
        return nextPosX;
    }

    /**
     * @return True if the spritebase can move otherwise false.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * Set whether the spritebase can move or not.
     * @param canMove 
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * @return The postion in nextRoom
     */
    public String getNextPos() {
        return nextPos;
    }

    /**
     * @return The image representing the spritebase.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Set the image to represent the spritebase.
     * @param image 
     */
    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
