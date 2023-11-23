package interface_adapter.swap_views.welcome;

import use_case.swap_views.welcome.SwapToWelcomeInputBoundary;

public class SwapToWelcomeController {
    SwapToWelcomeInputBoundary swapToWelcomeInteractor;

    public SwapToWelcomeController(SwapToWelcomeInputBoundary swapToWelcomeInteractor) {
        this.swapToWelcomeInteractor = swapToWelcomeInteractor;
    }

    public void execute() {
        swapToWelcomeInteractor.execute();
    }
}
