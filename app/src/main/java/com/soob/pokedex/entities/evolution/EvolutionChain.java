package com.soob.pokedex.entities.evolution;

import java.util.LinkedList;

/**
 * Class holding details of a Pokemon's evolution chain. Because there are different ways of evolving,
 * different numbers of stages, along with using different artwork etc all of which is coming from
 * different places, this makes it easier to have everything in a single place
 */
public class EvolutionChain
{
    // TODO: Just getting everything working with the standard 3 stage evolution for now
    // TODO: Another where we might not need the Linked impl anymore
    LinkedList<EvolutionChainStage> stages;

    /**
     * Flag denotes whether an evolution chain is 'standard' i.e. is it one stage after another
     * without different potential pathS
     */
    boolean isStandardChain;

    public EvolutionChain()
    {
        // empty constructor
    }

    public EvolutionChain(LinkedList<EvolutionChainStage> stages, boolean isStandardChain)
    {
        this.stages = stages;
        this.isStandardChain = isStandardChain;
    }

    public LinkedList<EvolutionChainStage> getStages()
    {
        return stages;
    }

    public void setStages(LinkedList<EvolutionChainStage> stages)
    {
        this.stages = stages;
    }

    public boolean isStandardChain() {
        return isStandardChain;
    }

    public void setStandardChain(boolean standardChain) {
        isStandardChain = standardChain;
    }
}
