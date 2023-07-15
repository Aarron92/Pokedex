package com.soob.pokedex.entities.evolution;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Class for handling the different triggers for a Pokemon's evolution
 *
 * This class is intended to be created via Jackson mapper. Some fields are read direclty from the
 * top level whereas others are nested so are set further down in dedicated methods
 */
public class EvolutionTrigger
{
    String pokemonName; // not a JSON property, passed in separately

    String evolutionTriggerType;

    @JsonProperty("min_level")
    Integer minimumLevel;

    @JsonProperty("gender")
    Integer gender;

    String item; // item to used directly on a Pokemon to cause evolution e.g. Fire Stone

    String heldItem; // item to hold to cause evolution e.g. Metal Coat

    String knownMove;

    String knownMoveType;

    String location;

    @JsonProperty("min_affection")
    Integer minimumAffection;

    @JsonProperty("min_beauty")
    Integer minimumBeauty;

    @JsonProperty("min_happiness")
    Integer minimumHappiness;

    @JsonProperty("needs_overworld_rain")
    boolean needsOverworldRain;

    String speciesInParty;

    String typeInParty;

    @JsonProperty("relative_physical_stats")
    Integer relativePhysicalStats; // relation between the Pokémon's Attack and Defense stats

    @JsonProperty("time_of_day")
    String timeOfDay;

    String tradeSpecies;

    @JsonProperty("turn_upside_down")
    boolean turnUpsideDown;

    public EvolutionTrigger()
    {
        // empty constructor
    }

    @JsonProperty("trigger")
    private void mapTriggerName(Map<String, String> trigger)
    {
        if(trigger != null)
        {
            this.evolutionTriggerType = trigger.get("name");
        }
    }

    @JsonProperty("item")
    private void mapItem(Map<String, String> item)
    {
        if(item != null)
        {
            this.item = item.get("name");
        }
    }

    @JsonProperty("held_item")
    private void mapHeldItem(Map<String, String> heldItem)
    {
        if(heldItem != null)
        {
            this.heldItem = heldItem.get("name");
        }
    }

    @JsonProperty("known_move")
    private void mapKnownMove(Map<String, String> knownMove)
    {
        if(knownMove != null)
        {
            this.knownMove = knownMove.get("name");
        }
    }

    @JsonProperty("known_move_type")
    private void mapKnownMoveType(Map<String, String> knownMoveType)
    {
        if(knownMoveType != null)
        {
            this.knownMoveType = knownMoveType.get("name");
        }
    }

    @JsonProperty("location")
    private void mapLocation(Map<String, String> location)
    {
        if(location != null)
        {
            this.location = location.get("name");
        }
    }

    @JsonProperty("party_species")
    private void mapSpeciesInParty(Map<String, String> speciesInParty)
    {
        if(speciesInParty != null)
        {
            this.speciesInParty = speciesInParty.get("name");
        }
    }

    @JsonProperty("party_type")
    private void mapTypeInParty(Map<String, String> typeInParty)
    {
        if(typeInParty != null)
        {
            this.typeInParty = typeInParty.get("name");
        }
    }

    @JsonProperty("trade_species")
    private void mapTradeSpecies(Map<String, String> tradeSpecies)
    {
        if(tradeSpecies != null)
        {
            this.tradeSpecies = tradeSpecies.get("name");
        }
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getEvolutionTriggerType()
    {
        return evolutionTriggerType;
    }

    public void setEvolutionTriggerType(String evolutionTriggerType)
    {
        this.evolutionTriggerType = evolutionTriggerType;
    }

    public Integer getMinimumLevel()
    {
        return minimumLevel;
    }

    public void setMinimumLevel(Integer minimumLevel)
    {
        this.minimumLevel = minimumLevel;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(String heldItem) {
        this.heldItem = heldItem;
    }

    public String getKnownMove() {
        return knownMove;
    }

    public void setKnownMove(String knownMove) {
        this.knownMove = knownMove;
    }

    public String getKnownMoveType() {
        return knownMoveType;
    }

    public void setKnownMoveType(String knownMoveType) {
        this.knownMoveType = knownMoveType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMinimumAffection() {
        return minimumAffection;
    }

    public void setMinimumAffection(Integer minimumAffection) {
        this.minimumAffection = minimumAffection;
    }

    public Integer getMinimumBeauty() {
        return minimumBeauty;
    }

    public void setMinimumBeauty(Integer minimumBeauty) {
        this.minimumBeauty = minimumBeauty;
    }

    public Integer getMinimumHappiness() {
        return minimumHappiness;
    }

    public void setMinimumHappiness(Integer minimumHappiness) {
        this.minimumHappiness = minimumHappiness;
    }

    public boolean needsOverworldRain() {
        return needsOverworldRain;
    }

    public void setNeedsOverworldRain(boolean needsOverworldRain) {
        this.needsOverworldRain = needsOverworldRain;
    }

    public String getSpeciesInParty() {
        return speciesInParty;
    }

    public void setSpeciesInParty(String speciesInParty) {
        this.speciesInParty = speciesInParty;
    }

    public String getTypeInParty() {
        return typeInParty;
    }

    public void setTypeInParty(String typeInParty) {
        this.typeInParty = typeInParty;
    }

    public Integer getRelativePhysicalStats() {
        return relativePhysicalStats;
    }

    public void setRelativePhysicalStats(Integer relativePhysicalStats) {
        this.relativePhysicalStats = relativePhysicalStats;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getTradeSpecies() {
        return tradeSpecies;
    }

    public void setTradeSpecies(String tradeSpecies) {
        this.tradeSpecies = tradeSpecies;
    }

    public boolean turnUpsideDown() {
        return turnUpsideDown;
    }

    public void setTurnUpsideDown(boolean turnUpsideDown) {
        this.turnUpsideDown = turnUpsideDown;
    }
}