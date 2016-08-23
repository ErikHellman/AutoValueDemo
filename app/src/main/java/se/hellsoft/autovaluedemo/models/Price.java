package se.hellsoft.autovaluedemo.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Price implements Parcelable {
    public static Price create(long amount, String currency) {
        return new AutoValue_Price(amount, currency);
    }

    public static JsonAdapter<Price> jsonAdapter(Moshi moshi) {
        return new AutoValue_Price.MoshiJsonAdapter(moshi);
    }

    public abstract long amount();

    public abstract String currency();
}
