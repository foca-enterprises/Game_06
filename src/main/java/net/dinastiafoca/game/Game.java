package net.dinastiafoca.game;

import net.dinastiafoca.entity.EntityFactory;
import net.dinastiafoca.entity.entities.player.EntityPlayer;
import net.dinastiafoca.entity.entities.player.PlayerController;
import net.dinastiafoca.input.Keyboard;
import net.dinastiafoca.window.Camera;
import net.dinastiafoca.window.Window;
import net.dinastiafoca.window.renderer.Renderer;
import net.dinastiafoca.world.World;
import net.dinastiafoca.world.impl.SimpleWorld;

public class Game implements BaseGame {
    public Renderer renderer;
    public Camera camera;

    private Window window;
    private EntityFactory entityFactory;
    private EntityPlayer player;
    private PlayerController controller;
    private Keyboard keyboard;
    private World world;

    @Override
    public void onCreate() {
        window = new Window.WindowBuilder()
                .setTitle("--")
                .setWidth(800)
                .setHeight(600)
                .build();

        renderer = new Renderer(window);

        world = new SimpleWorld(250, 250, 256);

        camera = new Camera();

        entityFactory = new EntityFactory(world, this);

        keyboard = new Keyboard();
        window.addKeyboard(keyboard);

        player = entityFactory.createPlayer(86, 86);
        controller = new PlayerController(player, this);

        world.getDimension(0).addEntity(player);
    }

    @Override
    public void doTick() {
        world.update();
        controller.update();
    }

    @Override
    public void doRender() {
        renderer.begin();
        world.render(renderer, camera);
        renderer.end();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Keyboard getKeyboard() {
        return keyboard;
    }

    public World getWorld()
    {
        return world;
    }

    public Window getWindow()
    {
        return window;
    }
}
