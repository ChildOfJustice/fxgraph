package ru.yuldashev.learning.mai.exceptions;

public class DuplicatedNodeException extends GraphCommandException{
    public DuplicatedNodeException(String message){
        super(message);
    }
}
