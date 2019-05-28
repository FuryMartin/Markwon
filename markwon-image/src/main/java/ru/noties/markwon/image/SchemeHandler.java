package ru.noties.markwon.image;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * @since 3.0.0
 */
public abstract class SchemeHandler {

    /**
     * Changes since 4.0.0-SNAPSHOT:
     * <ul>
     * <li>Returns `non-null` image-item</li>
     * </ul>
     *
     * @see ImageItem#withResult(android.graphics.drawable.Drawable)
     * @see ImageItem#withDecodingNeeded(String, java.io.InputStream)
     */
    @NonNull
    public abstract ImageItem handle(@NonNull String raw, @NonNull Uri uri);

    /**
     * @since 4.0.0-SNAPSHOT
     */
    @NonNull
    public abstract Collection<String> supportedSchemes();
}
