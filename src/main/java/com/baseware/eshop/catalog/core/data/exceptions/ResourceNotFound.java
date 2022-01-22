package com.baseware.eshop.catalog.core.data.exceptions;

/**
 * Indicate a Resource is not found.
 */
public class ResourceNotFound extends RuntimeException {
   public ResourceNotFound(String message) {
     super(message);
   }
}
