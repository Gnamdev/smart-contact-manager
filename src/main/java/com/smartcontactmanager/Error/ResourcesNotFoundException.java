package com.smartcontactmanager.Error;

public class ResourcesNotFoundException extends RuntimeException{
    public ResourcesNotFoundException(String message){
        super(message);
    }
    public ResourcesNotFoundException(){
        super("ResourcesNotFoundException");
    }
}
