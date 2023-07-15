package com.soob.pokedex.enums;

public enum EvoArrowImgEnum
{
    RIGHT_ARROW("Right arrow for standard evolution"),
    DIAGONAL_UP_RIGHT_ARROW("Diagonally up and right arrow for evolution chain with multiple possible stages"),
    DIAGONAL_DOWN_RIGHT_ARROW("Diagonally down and right arrow for evolution chain with multiple possible stages");

    private String description;

    EvoArrowImgEnum(String description)
    {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
