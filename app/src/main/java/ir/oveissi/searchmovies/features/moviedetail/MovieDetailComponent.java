package ir.oveissi.searchmovies.features.moviedetail;


import dagger.Subcomponent;


@Subcomponent(modules = {
        MovieDetailPresenterModule.class
})
public interface MovieDetailComponent {

    void inject(MovieDetailActivity movieDetailActivity);
}