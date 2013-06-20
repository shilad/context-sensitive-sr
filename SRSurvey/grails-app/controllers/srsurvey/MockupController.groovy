package srsurvey

class MockupController {

    def index() {}

    def ratings() {
        render(view : 'ratings')
    }
    def interest() {
        render(view : 'interest')
    }
    def comments() {
        render(view : 'comments')
    }
    def disclaimer() {
        render(view : 'disclaimer')
    }

    def expertise() {
        render(view: 'expertise')
    }
}
