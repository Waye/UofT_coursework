package model;

import java.util.List;

/** this is finder interface. use this as dependency injection to find images under tags. */
public interface Finder {
  /**
   * find all images under tags.
   *
   * @return all images.
   */
  List<Images> findAll();
}
