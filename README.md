# Digital Surgery Android Tech Test

## Brief Explanation of My Solution & Design Choices

The project is structured into the following **modules**:

- **shared** (common logic used across all app variants)
    - `database` – Room database configuration
    - `networking` – Retrofit and OkHttp setup, API services
    - `procedures` – Logic related to procedures
    - `app` – Main screen UI and ViewModel, utilizing the shared modules to achieve the desired
      functionality
- **apps** (client-specific configurations)
    - `digitalsurgery` – First variant of the application
    - `surgicalinsights` – Alternative variant of the application

### Why I Chose a Multi-Module Approach Over Gradle Flavors

Using **separate modules** for different clients offers several advantages over Gradle product
flavors:

- **Scalability** – Each app variant can be easily customized (e.g., colors, fonts, app name, and
  package ID).
- **Feature Modularity** – Features can be implemented in separate modules, allowing each app to
  decide which ones to include.
- **Code Simplicity** – There’s no hidden logic or complex conditionals specific to each app; we
  simply include the required modules.
- **Easier Expansion** – New app variants can be added without modifying existing ones.
- **Better Environment Management** – Many projects require multiple instances per app (e.g.,
  production and staging). Using flavors for that while keeping app variants in separate modules
  avoids an unmanageable number of flavor combinations.

While this approach comes with some **code duplication**, I believe it's a worthwhile trade-off. As
differences between app variants grow, this modular structure becomes even more beneficial. Though
it may seem excessive for a simple test task, it’s a **better fit for real-world applications**
where maintainability and scalability are key.

### Additional Notes

- **UseCases** are used to encapsulate logic for handling **networking and database operations**.
- **Database Testing** – Several tests were written using **Room’s in-memory database** to verify *
  *table integrity and SQL queries**.

### Things to Improve

- **Refactor Entity Mapping** – Use a dedicated `Mapper` class to handle entity mapping and write
  unit tests for it.
- **Enhance Error Handling** – Make error messages more informative by creating error types based on
  API response codes.
- **Modularize UI Components** – Move procedure-related UI components to the `procedures` module for
  better separation of concerns.
- **Introduce a `design` Module** – Create a `design` module to house common UI components, such as
  `FavoriteIconSwitch`, for better reusability.
- **Improve Testing Coverage** –
    - Add unit tests for `GetProcedureIdsUseCase`.
    - Increase database test coverage to ensure all interactions are fully tested.

---

## Original Tech Test Requirements

We would like you to spend a few hours on the following technical task—create an Android application
with two screens, some API requests, and a bit of database storing.

---

## Procedure List Page

This should be the home page of the app and it should show a list of procedure cards/cells, each
with:

- A **thumbnail image**
- The **procedure title**
- The **phase count**
- A **favourite button toggle**

Clicking on one of the procedure items should show the user a **bottom sheet** dismissible view,
containing all the detailed information of the procedure:

- **Card image** at the top
- **Favourite button toggle**
- **Title of the procedure**
- **Total duration** in minutes
- **Creation date** in `dd/mm/yyyy` format
- A **horizontal list of phases**, each containing:
    - **Thumbnail image**
    - **Phase name**

---

## Favourite Procedures Page

Each time a user taps on the favourite button toggle, that procedure has to be stored in a **local
database**. This favourite procedures list will display only the procedures marked as **favourites
** (if any).

- Procedures must be **tappable** so that the user is able to see the **procedure details view**.
- Each time a procedure's favourite state changes, the user should see a **Snackbar notification**
  showing the result.

---

## White Label Challenge

In addition to the existing requirements, we would like you to **make the app configurable for
multiple clients** using a **white-label approach**.

Each client should have **different branding and functionality**, while using the **same codebase**.

### Requirements:

- The app should support at least **two different clients**.
- Each client should have a **different visual style** (colors, logos, themes, etc.).
- The app should behave **differently per client**:
    - **Client A:** Procedures should be displayed in a **list**.
    - **Client B:** Procedures should be displayed in a **grid**.

Your solution should allow the app to scale and support additional clients in the future.

---

## Instructions

- This is your **fork of the project**. Feel free to push to the main branch.
- Upon completion, **push to main** and email us to let us know you have finished.
- **Do not include build folders or IDE-specific files.**

---

## Endpoints

- Procedures list: `https://staging.touchsurgery.com/api/v3/procedures`
- Procedure details: `https://staging.touchsurgery.com/api/v3/procedures/$PROCEDURE_ID`
    - Example: `https://staging.touchsurgery.com/api/v3/procedures/procedure-TSC_CemCup`

---

## Considerations

- **Production-ready code** → Write your code as if it were going to be published.
- Use the **latest stable Android Studio** version if possible.
- Provide a **brief explanation** of your solution, including **why** you made certain choices.

---

## Automated Tests

- Make sure your code is **testable** and include **unit tests**.
- **TDD is not mandatory**, but we would like to see **some representative tests**.
- UI tests are welcome, but not required.

---

## Technologies & Techniques to Use

You will be joining an existing team with existing technology. We want to see how well you work with
these:

✅ **Dependency Injection** → Dagger/Hilt/Koin  
✅ **Database** → Room  
✅ **Networking** → Retrofit/KTor  
✅ **Async Programming** → Coroutines (or RxJava, if preferred)  
✅ **UI** → Jetpack Compose (or XML if you think it fits better)  
✅ **Architecture** → MVVM/MVI  
✅ **Testing** → Unit tests, UI testing with Espresso

Our current app uses **Coroutines and Jetpack Compose**, but we still have some **RxJava and
XML-based layouts**. Feel free to use what you think is best.

---

## Bonus Features (Optional but appreciated) 🎁

✅ **Search/filter functionality** for the procedures list.  
✅ **Offline mode**—ensure the app works when there’s no internet.

---

## Final Notes

This challenge is meant to **evaluate your architecture, code quality, and ability to build scalable
Android applications**. If you have any questions, feel free to reach out.

We’re excited to see your solution! 🚀
