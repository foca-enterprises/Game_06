package net.dinastiafoca.world.impl;

import net.dinastiafoca.entity.Entity;
import net.dinastiafoca.registry.Blocks;
import net.dinastiafoca.window.Camera;
import net.dinastiafoca.window.renderer.Renderer;
import net.dinastiafoca.world.Block;
import net.dinastiafoca.world.Dimension;
import net.dinastiafoca.world.World;

import java.util.ArrayList;
import java.util.List;

public class DimensionAdapter implements Dimension
{
  protected final List<Entity> entities = new ArrayList<>();
  protected final int[] blocks;

  protected final World world;

  protected final int WIDTH;
  protected final int HEIGHT;

  public DimensionAdapter(World world) {
    this.world = world;
    this.WIDTH = world.getWidth();
    this.HEIGHT = world.getHeight();

    this.blocks = new int[WIDTH * HEIGHT];
  }

  @Override
  public void update() {
    getEntities().forEach(Entity::update);
  }

  @Override
  public void render(Renderer renderer, Camera camera) {
    for(int block = 0; block < blocks.length; block++) {
      Blocks.getById(blocks[block]).render(renderer, camera.translateX((block % WIDTH) * Block.BLOCK_SIZE), camera.translateY((block / WIDTH) * Block.BLOCK_SIZE));
    }

    getEntities().forEach(entity -> entity.render(renderer, camera));
  }

  @Override
  public World getWorld() {
    return world;
  }

  @Override
  public Block getBlock(int x, int y) {
    int index = x + y * WIDTH;

    return index < blocks.length ? Blocks.getById(blocks[index]) : Blocks.AIR;
  }

  @Override
  public boolean addEntity(Entity entity) {
    return entities.add(entity);
  }

  @Override
  public boolean removeEntity(Entity entity) {
    return entities.remove(entity);
  }

  @Override
  public List<Entity> getEntities() {
    return new ArrayList<>(entities);
  }

  @Override
  public void setBlock(int x, int y, Block block) {
    if(x >= WIDTH || x < 0 || y >= HEIGHT || y < 0)
      throw new AssertionError();

    blocks[x + y * WIDTH] = block.getId();
  }
}
