/*******************************************************************************
 * Copyright (c) 2013 The University of Reading
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University of Reading, nor the names of the
 *    authors or contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

package uk.ac.rdg.resc.edal.dataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.rdg.resc.edal.dataset.plugins.VariablePlugin;
import uk.ac.rdg.resc.edal.exceptions.EdalException;
import uk.ac.rdg.resc.edal.metadata.VariableMetadata;

public abstract class AbstractDataset implements Dataset {
    private static final Logger log = LoggerFactory.getLogger(AbstractGridDataset.class);
    private String id;
    private Map<String, VariableMetadata> vars;
    private List<VariablePlugin> plugins;

    /**
     * @param id
     *            The ID to use for this {@link Dataset}
     * @param vars
     *            A {@link Collection} of {@link VariableMetadata} objects
     *            representing the data which will be stored in this
     *            {@link Dataset}
     */
    public AbstractDataset(String id, Collection<? extends VariableMetadata> vars) {
        this.id = id;
        this.vars = new LinkedHashMap<String, VariableMetadata>();
        this.plugins = new ArrayList<VariablePlugin>();
        for (VariableMetadata metadata : vars) {
            this.vars.put(metadata.getId(), metadata);
            metadata.setDataset(this);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public VariableMetadata getVariableMetadata(String variableId) {
        if (!vars.containsKey(variableId)) {
            log.error("Requested variable metadata for ID: " + variableId
                    + ", but this doesn't exist");
            throw new IllegalArgumentException(
                    "This dataset does not contain the specified variable (" + variableId + ")");
        }
        return vars.get(variableId);
    }

    @Override
    public Set<VariableMetadata> getTopLevelVariables() {
        Set<VariableMetadata> ret = new LinkedHashSet<VariableMetadata>();
        for (VariableMetadata metadata : vars.values()) {
            if (metadata.getParent() == null) {
                ret.add(metadata);
            }
        }
        return ret;
    }

    @Override
    public Set<String> getVariableIds() {
        return vars.keySet();
    }

    /*
     * Determines whether a variable is derived from a plugin and returns the
     * plugin, or null if it is a non-derived variable
     */
    protected VariablePlugin isDerivedVariable(String varId) {
        for (VariablePlugin plugin : plugins) {
            if (Arrays.asList(plugin.providesVariables()).contains(varId)) {
                return plugin;
            }
        }
        return null;
    }

    @Override
    public void addVariablePlugin(VariablePlugin plugin) throws EdalException {
        /*-
         * First check that the supplied plugin doesn't provide any variables
         * which either:
         * 
         * a) We already have in this dataset
         * b) Is generated by another plugin we already have
         */
        for (String generatedId : plugin.providesVariables()) {
            for (String alreadyHave : vars.keySet()) {
                if (alreadyHave.equals(generatedId)) {
                    throw new IllegalArgumentException(
                            "This dataset already contains the variable " + alreadyHave
                                    + " so this plugin cannot be added");
                }
            }
            for (VariablePlugin existingPlugin : plugins) {
                for (String alreadyHave : existingPlugin.providesVariables()) {
                    if (alreadyHave.equals(generatedId)) {
                        throw new IllegalArgumentException(
                                "This dataset already has a plugin which provides the variable "
                                        + alreadyHave + " so this plugin cannot be added");
                    }
                }
            }
        }
        /*
         * Now check that this dataset can supply all of the required variables.
         * 
         * At the same time, create an array of the VariableMetadata this plugin
         * uses for later use
         */
        VariableMetadata[] sourceMetadata = new VariableMetadata[plugin.usesVariables().length];
        int index = 0;
        for (String requiredId : plugin.usesVariables()) {
            if (!vars.keySet().contains(requiredId)) {
                throw new IllegalArgumentException("This plugin needs the variable " + requiredId
                        + ", but this dataset does not supply it.");
            }
            sourceMetadata[index++] = vars.get(requiredId);
        }

        plugins.add(plugin);

        /*-
         * The plugins have 2 functions:
         * 
         * 1) To generate data on-the-fly
         * 2) To insert metadata into the tree
         * 
         * For data, it's sufficient to store the plugin and use it when
         * required. For metadata, we immediately want to alter the metadata
         * tree and store the VariableMetadata in the map.
         * 
         * Do that here.
         */
        VariableMetadata[] variableMetadata = plugin.processVariableMetadata(sourceMetadata);

        for (VariableMetadata metadata : variableMetadata) {
            vars.put(metadata.getId(), metadata);
            metadata.setDataset(this);
        }
    }
}