package org.bitcoinj.wallet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.KeyCrypter;

import java.security.SecureRandom;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Hash Engineering on 8/6/2017.
 */
public class DeterministicKeyChain44 extends DeterministicKeyChain {

    /**
     * Generates a new key chain with entropy selected randomly from the given {@link java.security.SecureRandom}
     * object and the default entropy size.
     */
    public DeterministicKeyChain44(SecureRandom random) {
        super(random);
    }

    /**
     * Creates a deterministic key chain starting from the given seed. All keys yielded by this chain will be the same
     * if the starting seed is the same.
     */
    protected DeterministicKeyChain44(DeterministicSeed seed) {
        super(seed, null);
    }

    /**
     * For use in {@link KeyChainFactory} during deserialization.
     */
    protected DeterministicKeyChain44(DeterministicSeed seed, KeyCrypter keyCrypter) {
        super(seed, keyCrypter);
    }

    /**
     * <p>Creates a deterministic key chain with the given watch key. If <code>isFollowing</code> flag is set then this keychain follows
     * some other keychain. In a married wallet following keychain represents "spouse's" keychain.</p>
     * <p>Watch key has to be an account key.</p>
     */
    protected DeterministicKeyChain44(DeterministicKey watchKey, boolean isFollowing) {
        super(watchKey, isFollowing);
    }

    /** Override in subclasses to use a different account derivation path */
    protected ImmutableList<ChildNumber> getAccountPath() {
        return BIP44_ACCOUNT_ZERO_PATH;
    }

    public static class Builder<T extends DeterministicKeyChain44.Builder<T>> extends DeterministicKeyChain.Builder<T> {

        protected Builder() {
        }

        @Override
        public DeterministicKeyChain build() {
            checkState(random != null || entropy != null || seed != null || watchingKey!= null, "Must provide either entropy or random or seed or watchingKey");
            checkState(passphrase == null || seed == null, "Passphrase must not be specified with seed");
            DeterministicKeyChain chain;

            if (random != null) {
                // Default passphrase to "" if not specified
                chain = new DeterministicKeyChain(random, bits, getPassphrase(), seedCreationTimeSecs);
            } else if (entropy != null) {
                chain = new DeterministicKeyChain(entropy, getPassphrase(), seedCreationTimeSecs);
            } else if (seed != null) {
                seed.setCreationTimeSeconds(seedCreationTimeSecs);
                chain = new DeterministicKeyChain(seed);
            } else {
                watchingKey.setCreationTimeSeconds(seedCreationTimeSecs);
                chain = new DeterministicKeyChain(watchingKey);
            }

            return chain;
        }
    }
}
