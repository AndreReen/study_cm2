
package com.coffeemachine.core;

import com.coffeemachine.model.NonExistentEntityException;


public class NonExistentBeverageException extends NonExistentEntityException {

    public NonExistentBeverageException() {
        super("Customer does not exist");
    }
    
}
