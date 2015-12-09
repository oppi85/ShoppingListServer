/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rocho.shopinglistserver;

import java.net.URI;

/**
 *
 * @author Sven
 */
public class DbHelper {
    
    URI dbUri = null;

    public URI getDbUri() {
        return dbUri;
    }

    public void setDbUri(URI dbUri) {
        this.dbUri = dbUri;
    }
    
    
    
}
