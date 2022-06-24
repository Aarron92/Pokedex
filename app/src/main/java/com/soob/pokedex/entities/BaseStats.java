package com.soob.pokedex.entities;

/**
 * Class for Pokemon Base Stats
 */
public class BaseStats
{
    private int hp;
    private int attack;
    private int defence;
    private int specialAttack;
    private int specialDefence;
    private int speed;
    private int total;

    public BaseStats()
    {
    }

    public BaseStats(final int hp, final int attack, final int defence, final int specialAttack,
                     final int specialDefense, final int speed)
    {
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        this.specialAttack = specialAttack;
        this.specialDefence = specialDefense;
        this.speed = speed;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public int getAttack()
    {
        return attack;
    }

    public void setAttack(int attack)
    {
        this.attack = attack;
    }

    public int getDefence()
    {
        return defence;
    }

    public void setDefence(int defence)
    {
        this.defence = defence;
    }

    public int getSpecialAttack()
    {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack)
    {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefence()
    {
        return specialDefence;
    }

    public void setSpecialDefence(int specialDefence)
    {
        this.specialDefence = specialDefence;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getTotal()
    {
        return this.hp + this.attack + this.defence + this.specialAttack + this.specialDefence + this.speed;
    }
}
