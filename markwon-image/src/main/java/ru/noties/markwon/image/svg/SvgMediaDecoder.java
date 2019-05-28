package ru.noties.markwon.image.svg;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import ru.noties.markwon.image.DrawableUtils;
import ru.noties.markwon.image.MediaDecoder;

/**
 * @since 1.1.0
 */
public class SvgMediaDecoder extends MediaDecoder {

    public static final String CONTENT_TYPE = "image/svg+xml";

    /**
     * @see #create(Resources)
     * @since 4.0.0-SNAPSHOT
     */
    @NonNull
    public static SvgMediaDecoder create() {
        return new SvgMediaDecoder(Resources.getSystem());
    }

    @NonNull
    public static SvgMediaDecoder create(@NonNull Resources resources) {
        return new SvgMediaDecoder(resources);
    }

    private final Resources resources;

    @SuppressWarnings("WeakerAccess")
    SvgMediaDecoder(Resources resources) {
        this.resources = resources;
    }

    @NonNull
    @Override
    public Drawable decode(@Nullable String contentType, @NonNull InputStream inputStream) {

        final SVG svg;
        try {
            svg = SVG.getFromInputStream(inputStream);
        } catch (SVGParseException e) {
            throw new IllegalStateException("Exception decoding SVG", e);
        }

        final float w = svg.getDocumentWidth();
        final float h = svg.getDocumentHeight();
        final float density = resources.getDisplayMetrics().density;

        final int width = (int) (w * density + .5F);
        final int height = (int) (h * density + .5F);

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);
        canvas.scale(density, density);
        svg.renderToCanvas(canvas);

        final Drawable drawable = new BitmapDrawable(resources, bitmap);
        DrawableUtils.applyIntrinsicBounds(drawable);
        return drawable;
    }

    @NonNull
    @Override
    public Collection<String> supportedTypes() {
        return Collections.singleton(CONTENT_TYPE);
    }
}
