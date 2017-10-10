package org.bitcoinj.wallet;

import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;

/**
 * Created by Eric on 8/6/2017.
 */
public class DualWallet extends Wallet {
    /**
     * Creates a new, empty wallet with a randomly chosen seed and no transactions. Make sure to provide for sufficient
     * backup! Any keys will be derived from the seed. If you want to restore a wallet from disk instead, see
     * {@link #loadFromFile}.
     */
    public DualWallet(NetworkParameters params) {
        this(Context.getOrCreate(params));
    }

    /**
     * Creates a new, empty wallet with a randomly chosen seed and no transactions. Make sure to provide for sufficient
     * backup! Any keys will be derived from the seed. If you want to restore a wallet from disk instead, see
     * {@link #loadFromFile}.
     */
    public DualWallet(Context context) {
        super(context, new DualKeyChainGroup(context.getParams()));
    }

    public static DualWallet fromSeed(NetworkParameters params, DeterministicSeed seed) {
        return new DualWallet(params, new DualKeyChainGroup(params, seed));
    }

    public DualWallet(NetworkParameters params, KeyChainGroup keyChainGroup) {
        super(params, keyChainGroup);
    }
}

