package com.soob.pokedex.entities.evolution;

import java.util.LinkedList;
import java.util.List;

/**
 * Class holding details of a Pokemon's evolution chain. Because there are different ways of evolving,
 * different numbers of stages, along with using different artwork etc all of which is coming from
 * different places, this makes it easier to have everything in a single place
 */
public class EvolutionChain
{
    EvolutionChainStage stage1;

    /**
     * Stage 2 is a list because the second (sometimes final) stage of some Pokemon is 1 of several
     * potential Pokemon
     */
    List<EvolutionChainStage> stage2;

    /**
     * Stage 3 is a list because the third/final stage of some Pokemon is 1 of several potential
     * Pokemon
     */
    List<EvolutionChainStage> stage3;

    /**
     * Flag denotes whether an evolution chain is 'standard' i.e. is it one stage after another
     * without different potential pathS
     */
//    boolean isStandardChain;

    public EvolutionChain()
    {
        // empty constructor
    }

    public EvolutionChain(EvolutionChainStage stage1, List<EvolutionChainStage> stage2, List<EvolutionChainStage> stage3)
    {
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
    }

    public EvolutionChainStage getStage1() {
        return stage1;
    }

    public void setStage1(EvolutionChainStage stage1) {
        this.stage1 = stage1;
    }

    public List<EvolutionChainStage> getStage2() {
        return stage2;
    }

    public void setStage2(List<EvolutionChainStage> stage2) {
        this.stage2 = stage2;
    }

    public List<EvolutionChainStage> getStage3() {
        return stage3;
    }

    public void setStage3(List<EvolutionChainStage> stage3) {
        this.stage3 = stage3;
    }
}
