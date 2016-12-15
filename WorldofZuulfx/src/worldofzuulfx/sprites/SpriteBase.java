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
    private String nextTelePos;
    private double nextPosX;
    private double nextPosY;

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
     * The purpose of this method is that it is called in case of intersection.
     * It has to be overrided by sub-classes of the SpriteBase class.
     * @param spriteBase The SpriteBase that was intersected with.
     */
    public abstract void collides(SpriteBase spriteBase);

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
     * @param layer The spritebase layer
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
     * @param x The x-coordinate
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
     * @param y The y-coordinate
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
     * @param x X-position
     * @param y y-poistion
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
     * another spritebase to. CanTeleport is set True automatically.
     *
     * @param room The room which the spritebase is teleported to.
     * @param nextPos The position in the room.
     */
    public void setTeleport(Room room, String nextPos) {
        nextRoom = room;
        this.nextTelePos = nextPos;

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
     * @param nextPosX The x-position
     */
    public void setNextPosX(double nextPosX) {
        this.nextPosX = nextPosX;
    }

    /**
     * Set the next y-coordinate is used when moving to check if the spritebase
     * collides with another spritebase.
     *
     * @param nextPosY The y-position
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
     *
     * @param canMove Set true if the spritebase shall be able to move otherwise
     * false.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * @return The postion in nextRoom
     */
    public String getNextPos() {
        return nextTelePos;
    }

    /**
     * @return The image representing the spritebase.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Set the image to represent the spritebase.
     *
     * @param image The image to represent the spritebase.
     */
    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
