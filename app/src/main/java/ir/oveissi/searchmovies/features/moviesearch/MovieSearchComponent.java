package ir.oveissi.searchmovies.features.moviesearch;


import dagger.Subcomponent;


@Subcomponent(modules = {
        MovieSearchPresenterModule.class
})
public interface MovieSearchComponent {

    void inject(MovieSearchActivity movieSearchActivity);
}