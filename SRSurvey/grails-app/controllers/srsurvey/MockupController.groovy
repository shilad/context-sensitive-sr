package srsurvey

class MockupController {

    def index() {}

    def ratings() {
        render(view : 'ratings')
    }
    def comments() {
         render(view : 'comments')
    }
    def disclaimer() {
        render(view : '/mockup/disclaimer')
    }
    def interest() {
        render(view : 'interest')
    }
}
