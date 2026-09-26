# Private folders screen — Android XML resource pack

This pack recreates the selected minimal outlined **Private space** screen using a vertical `LinearLayout`; each folder card and the password action is also a horizontal `LinearLayout`. No bitmap/background image is required: `bg_private_screen.xml` draws the soft white-to-mint gradient natively.

## Files

- `res/layout/activity_private_folders.xml` — complete screen layout.
- `res/drawable/bg_private_screen.xml` — gradient page background.
- `res/drawable/bg_folder_card.xml` and `res/drawable/bg_password_outline.xml` — shape-only backgrounds for older devices.
- `res/drawable-v21/bg_folder_card.xml` and `res/drawable-v21/bg_password_outline.xml` — matching rounded ripple backgrounds for API 21+.
- `res/drawable/ic_*.xml` — lock, image, play, arrow, and key vector icons.
- `res/values/colors.xml`, `dimens.xml`, `strings.xml` — colors, dimensions, and display/accessibility text.

## Install

1. Copy this pack's `res` folder contents into your Android app's `app/src/main/res/`, merging folders with existing resources.
2. Open the layout in your activity:

   ```java
   setContentView(R.layout.activity_private_folders);
   ```

   Kotlin:

   ```kotlin
   setContentView(R.layout.activity_private_folders)
   ```

3. Attach your existing navigation/password actions to the clickable container IDs:

   ```java
   findViewById(R.id.imagesCard).setOnClickListener(v -> openImages());
   findViewById(R.id.videosCard).setOnClickListener(v -> openVideos());
   findViewById(R.id.changePasswordButton).setOnClickListener(v -> openChangePassword());
   ```

   Replace `openImages()`, `openVideos()`, and `openChangePassword()` with your app's own destinations or handlers. The XML only supplies the view layer; it does not access files or implement authentication.

## Customization

- Change sample counts in `res/values/strings.xml` (or replace those labels with runtime-bound counts).
- Update palette in `res/values/colors.xml`.
- Adjust spacing in `res/values/dimens.xml`; the folder rows are currently `124dp` high and the password action is `60dp` high.
- The API 21+ ripple color is `ripple_teal`. The ripple mask has the same corner radius as the visible shape so it remains clipped to the card/pill.
- Keep the fallback files in `res/drawable/` if supporting API levels below 21. Android automatically selects the `drawable-v21/` versions on API 21+.

## Vector icon compatibility

The icon assets are Android VectorDrawables. If your project supports pre-API 21 devices, enable Android Gradle Plugin vector support in the app module:

```gradle
android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}
```

With AppCompat on older devices, use `AppCompatImageView` and `app:srcCompat` if needed. On projects with minimum API 21+, the supplied `ImageView` + `android:src` usage is suitable.
