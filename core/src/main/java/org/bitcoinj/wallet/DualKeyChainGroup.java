package org.bitcoinj.wallet;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.NetworkParameters;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by HashEngineering on 8/6/2017.
 *
 * This will add a BIP32 Key chain and a BIP44 Key chain (default)
 */
public class DualKeyChainGroup extends KeyChainGroup {

    static public ImmutableList<DeterministicKeyChain> bothBIP32andBIP44chains(DeterministicSeed seed) {
        return ImmutableList.of(new DeterministicKeyChain(seed), new DeterministicKeyChain44(seed));
    }

    /** Creates a keychain group with no basic chain, and a single, lazily created HD chain. */
    public DualKeyChainGroup(NetworkParameters params) {
        super(params, null, new ArrayList<DeterministicKeyChain>(2), null, null);
    }

    /** Creates a keychain group with no basic chain, and an HD chain initialized from the given seed. */
    public DualKeyChainGroup(NetworkParameters params, DeterministicSeed seed) {
        super(params, null, bothBIP32andBIP44chains(seed), null, null);
    }

    /** Adds a new HD chain to the chains list, and make it the default chain (from which keys are issued). */
    @Override
    public void createAndActivateNewHDChain() {
        // We can't do auto upgrade here because we don't know the rotation time, if any.
        super.createAndActivateNewHDChain();  // add the default chain

        //Add the BIP44Chain.
        final DeterministicKeyChain chain = new DeterministicKeyChain44(getActiveKeyChain().getSeed());
        addAndActivateHDChain(chain);
    }
}
